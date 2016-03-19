CREATE SCHEMA IF NOT EXISTS wishlist;

DROP TABLE IF EXISTS wishlist.Friends;
DROP TABLE IF EXISTS wishlist.Presents;
DROP TABLE IF EXISTS wishlist.Users;

USE wishlist;

CREATE TABLE wishlist.Users(
	UserID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	Login NVARCHAR(20) NOT NULL,
  PasswordHash BINARY(64) NOT NULL
);
CREATE TABLE wishlist.Presents (
	PresentID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	Name NVARCHAR(40) NOT NULL,
  Description NVARCHAR(255),
	UserID INT NOT NULL,
	FOREIGN KEY (UserID) REFERENCES Users(UserID)
);
CREATE TABLE wishlist.Friends (
	UserID INT NOT NULL,
	FriendID INT NOT NULL,
	FOREIGN KEY (UserID) REFERENCES Users(UserID),
	FOREIGN KEY (FriendID) REFERENCES Users(UserID)
);

