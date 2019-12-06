#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <time.h>
#include <unistd.h>

#define PORT 25003

void logger(char tag[], char message[])
{
	FILE *f;
	char tm_buff[26];
	struct tm *tm_info;
	time_t now;
	time(&now);
	tm_info = localtime(&now);

	strftime(tm_buff, 26, "%Y-%m-%d %H:%M:%S", tm_info);

	f = fopen("logs/server.log", "a");
	printf("%s [%s]: %s\n", tm_buff, tag, message);
	fprintf(f, "%s [%s]: %s\n", tm_buff, tag, message);
	fclose(f);
	bzero(tm_buff, 26);
}

void create_dir(char name[])
{
	struct stat st = {0};

	if (stat(name, &st) == -1)
	{
		mkdir(name, 0700);
	}
}

int main(int argc, char const *argv[])
{
	int server_fd, new_socket, valread;
	struct sockaddr_in address;
	int opt = 1;
	int addrlen = sizeof(address);
	char buffer[16] = {0};
	char *downloads_path;
	char *name_with_extension;
	char *extension = ".png";

	create_dir("logs");
	create_dir("downloads");

	if (argc > 2)
	{
		logger("ERROR", "Too many arguments");
		return 0;
	}
	else if (argc <= 1)
	{
		logger("ERROR", "Proper usage: ./program filename");
		return 0;
	}

	name_with_extension = malloc(strlen(argv[1]) + 1 + 4);
	strcpy(name_with_extension, argv[1]);
	strcat(name_with_extension, extension);

	downloads_path = malloc(10 + strlen(name_with_extension));
	strcpy(downloads_path, "downloads/");
	strcat(downloads_path, name_with_extension);

	logger("INFO", "Creating socket file descriptor");
	if ((server_fd = socket(AF_INET, SOCK_STREAM, 0)) == 0)
	{
		logger("ERROR", "Couldn't create socket");
		exit(EXIT_FAILURE);
	}

	logger("INFO", "Attaching socket to the port 25003");
	if (setsockopt(server_fd, SOL_SOCKET, SO_REUSEADDR, &opt, sizeof(opt)))
	{
		logger("ERROR", "Problem occured while attaching socket to the port 25003");
		exit(EXIT_FAILURE);
	}

	address.sin_family = AF_INET;
	address.sin_addr.s_addr = INADDR_ANY;
	address.sin_port = htons(PORT);

	logger("INFO", "Binding");
	if (bind(server_fd, (struct sockaddr *)&address, sizeof(address)) < 0)
	{
		logger("ERROR", "Bind failed");
		exit(EXIT_FAILURE);
	}

	logger("INFO", "Listening for connections");
	if (listen(server_fd, 3) < 0)
	{
		logger("ERROR", "Problem occured while listening");
		exit(EXIT_FAILURE);
	}

	if ((new_socket = accept(server_fd, (struct sockaddr *)&address, (socklen_t *)&addrlen)) < 0)
	{
		logger("ERROR", "Problem occured while accepting a connection");
		exit(EXIT_FAILURE);
	}

	FILE *fp = fopen(downloads_path, "wb");

	logger("INFO", "Waiting for data");
	while ((valread = (int)read(new_socket, buffer, 16)) > 0)
	{
		if (valread < 0)
		{
			logger("ERROR", "Problem occured while receiving data");
			exit(EXIT_FAILURE);
		}

		for (int i = 0; i < 16; i += 2)
		{
			unsigned char val;
			char tmp_hexbuf[3] = {buffer[i], buffer[i + 1], 0};
			val = (unsigned char)strtol(tmp_hexbuf, NULL, 16);
			fputc(val, fp);
		}
		bzero(buffer, 16);
	}
	logger("INFO", "Image saved in /downloads");
	fclose(fp);

	logger("INFO", "Closing socket");
	close(server_fd);
	logger("INFO", "Socket closed");
	return 0;
}