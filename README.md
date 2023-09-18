# Command-Line Email Client

## Overview

This command-line-based email client is a graded assignment that demonstrates the use of Java for managing recipients and sending emails. The client supports two types of recipients: official and personal, with the ability to add, update, and send messages to them. Additionally, the client automatically sends birthday greetings to recipients on their special day.

## Features

- **Recipient Management**: Easily manage recipients categorized as official, office friends, and personal. Recipient details are stored in a text file.

- **Adding Recipients**: Add new recipients via the command-line, and their details will be updated in the text file.

- **Birthday Greetings**: The client sends automated birthday greetings to recipients on their birthday. Official friends and personal recipients receive different messages.

- **Serialization**: All sent emails are serialized and stored on the hard disk for future reference.

- **Command-Line Options**: The client supports several command-line options:
  - Add a new recipient.
  - Send an email to a recipient.
  - Print the names of recipients with birthdays set to the current date.
  - Retrieve details of sent emails on a specified date.
  - Check the number of recipient objects in the application.

## Getting Started

1. Clone this repository to your local machine.
2. Open the project in your preferred Java development environment.
3. Compile and run the `Email_Client.java` file to start the email client.

## Requirements

- JavaMail API: Download the javax.mail.jar from https://javaee.github.io/javamail/#Download_JavaMail_Release


