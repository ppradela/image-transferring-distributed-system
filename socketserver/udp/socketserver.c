#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <time.h>

#define PORT 9999
#define MAXLINE 16

void logger(char tag[], char message[]) {
    FILE *f;
    char tm_buff[26];
    struct tm* tm_info;
    time_t now;
    time(&now);
    tm_info = localtime(&now);

    strftime(tm_buff, 26, "%Y-%m-%d %H:%M:%S", tm_info);

    f = fopen("logs.log", "a+");
    printf("%s [%s]: %s\n", tm_buff, tag, message);   
    fprintf(f, "%s [%s]: %s\n", tm_buff, tag, message);
    fclose(f);
    bzero(tm_buff,26);
}

int main(int argc, char** argv)
{
    int sockfd;
    char buffer[MAXLINE];
    struct sockaddr_in servaddr, cliaddr;
    char* name_with_extension;
    char* extension = ".png";

    if (argc > 2) {
        logger("ERROR","Too many arguments");
        return 0;
    }
    else if (argc <= 1) {
        logger("ERROR","Proper usage: ./program filename");
        return 0;
    }

    name_with_extension = malloc(strlen(argv[1])+1+4);
    strcpy(name_with_extension, argv[1]);
    strcat(name_with_extension, extension);

    logger("INFO", "Creating socket file descriptor");
    if ((sockfd = socket(AF_INET, SOCK_DGRAM, 0)) < 0) {
        logger("ERROR","Socket creation failed");
        exit(EXIT_FAILURE);
    }

    memset(&servaddr, 0, sizeof(servaddr));
    memset(&cliaddr, 0, sizeof(cliaddr));

    servaddr.sin_family = AF_INET;
    servaddr.sin_addr.s_addr = INADDR_ANY;
    servaddr.sin_port = htons(PORT);

    logger("INFO", "Binding the socket with the address");
    if (bind(sockfd, (const struct sockaddr*)&servaddr, sizeof(servaddr)) < 0) {
        logger("ERROR","Bind failed");
        exit(EXIT_FAILURE);
    }

    FILE *fp = fopen(name_with_extension, "wb+");

    logger("INFO", "Waiting for data");
    int len, n;
    while ((n = recvfrom(sockfd, (char*)buffer, MAXLINE, MSG_WAITALL, (struct sockaddr*)&cliaddr, &len)) > 0) {
        buffer[n] = '\0';
        printf("%s", buffer);
        bzero(buffer, MAXLINE);
    }
    logger("INFO", "Image saved");    
    fclose(fp);

    logger("INFO", "Closing socket");
    close(sockfd);
    logger("INFO", "Socket closed");
    return 0;
}