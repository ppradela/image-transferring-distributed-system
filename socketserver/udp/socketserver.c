#include <stdio.h> 
#include <stdlib.h> 
#include <unistd.h> 
#include <string.h> 
#include <sys/types.h> 
#include <sys/socket.h> 
#include <arpa/inet.h> 
#include <netinet/in.h> 

#define PORT 9999 
#define BUFSIZE 16 

int main() { 
    int sockfd; 
    char buffer[BUFSIZE];
    struct sockaddr_in servaddr, cliaddr; 
      
    // Creating socket file descriptor 
    if ( (sockfd = socket(AF_INET, SOCK_DGRAM, 0)) < 0 ) 
    { 
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
    if (bind(sockfd, (const struct sockaddr *)&servaddr, sizeof(servaddr)) < 0) 
    { 
        perror("bind failed"); 
        exit(EXIT_FAILURE); 
    } 

    while (1)
    {
    int len, n;
    n = recvfrom(sockfd, (char *)buffer, BUFSIZE, MSG_WAITALL, (struct sockaddr *) &cliaddr, &len);
    buffer[n]='\0';
    printf("%s",buffer);
    }
      
    close(sockfd);

    return 0; 
} 