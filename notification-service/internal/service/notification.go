package notification_service

import (
	"context"
	"log"
	"net/smtp"
	pb "notification-service/proto/generated"
	"os"
)

type NotificationServiceImpl struct {
	pb.UnimplementedNotificationServiceServer
}

func (s *NotificationServiceImpl) SendEmail(ctx context.Context, req *pb.EmailRequest) (*pb.EmailResponse, error) {
	email := req.GetEmail()
	message := req.GetMessage()

	log.Printf("Sending email to %s with message: %s", email, message)

	smtpHost := os.Getenv("MAIL_HOST")
	smtpPort := os.Getenv("MAIL_PORT")
	emailUsername := os.Getenv("EMAIL_USERNAME")

	auth := smtp.PlainAuth("", emailUsername, os.Getenv("EMAIL_PASSWORD"), smtpHost)

	msg := []byte("From: " + emailUsername + "\r\n" +
		"To: " + email + "\r\n" +
		"Subject: ChampZone Message\r\n" +
		"\r\n" +
		message + "\r\n")

	err := smtp.SendMail(smtpHost+":"+smtpPort, auth, emailUsername, []string{email}, msg)
	if err != nil {
		log.Printf("Error sending email: %v", err)
		return &pb.EmailResponse{Success: false, Error: err.Error()}, nil
	}

	return &pb.EmailResponse{Success: true}, nil
}
