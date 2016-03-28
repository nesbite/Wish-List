CREATE SCHEMA IF NOT EXISTS wishlist;

# DROP TABLE IF EXISTS wishlist.Friends;
# DROP TABLE IF EXISTS wishlist.Gifts;
# DROP TABLE IF EXISTS wishlist.Users;

USE wishlist;

CREATE TABLE IF NOT EXISTS wishlist.Users(
	UserID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	Login NVARCHAR(20) NOT NULL,
  PasswordHash BINARY(64) NOT NULL
);
CREATE TABLE IF NOT EXISTS wishlist.Gifts (
	GiftID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	Name NVARCHAR(40) NOT NULL,
  Description NVARCHAR(255),
	UserID INT NOT NULL,
	FOREIGN KEY (UserID) REFERENCES Users(UserID)
);
CREATE TABLE IF NOT EXISTS wishlist.Friends (
	UserID INT NOT NULL,
	FriendID INT NOT NULL,
	FOREIGN KEY (UserID) REFERENCES Users(UserID),
	FOREIGN KEY (FriendID) REFERENCES Users(UserID)
);
