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

void logger(char* tag, char* message) {
   FILE *f;
   time_t now;
   time(&now);
   fopen("logs.log", "a+");
   printf("%s [%s]: %s\n", ctime(&now), tag, message);   
   fprintf(f, "%s [%s]: %s\n", ctime(&now), tag, message);
}

int main(int argc, char** argv)
{
    int sockfd;
    char buffer[MAXLINE];
    struct sockaddr_in servaddr, cliaddr;

    if (argc > 2) {
        logger("ERROR","Too many arguments");
        return 0;
    }
    else if (argc <= 1) {
        logger("ERROR","Proper usage: ./program filename");
        return 0;
    }

    // Creating socket file descriptor
    if ((sockfd = socket(AF_INET, SOCK_DGRAM, 0)) < 0) {
        perror("socket creation failed");
        exit(EXIT_FAILURE);
    }

    memset(&servaddr, 0, sizeof(servaddr));
    memset(&cliaddr, 0, sizeof(cliaddr));

    // Filling server information
    servaddr.sin_family = AF_INET;
    servaddr.sin_addr.s_addr = INADDR_ANY;
    servaddr.sin_port = htons(PORT);

    // Bind the socket with the server address
    if (bind(sockfd, (const struct sockaddr*)&servaddr, sizeof(servaddr)) < 0) {
        perror("bind failed");
        exit(EXIT_FAILURE);
    }

    int len, n;
    while ((n = recvfrom(sockfd, (char*)buffer, MAXLINE, MSG_WAITALL, (struct sockaddr*)&cliaddr, &len)) > 0) {
        buffer[n] = '\0';
        printf("%s", buffer);
        bzero(buffer, MAXLINE);
    }
    close(sockfd);
    return 0;
}