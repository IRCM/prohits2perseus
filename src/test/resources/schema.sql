--
-- Copyright (c) 2006 Institut de recherches cliniques de Montreal (IRCM)
--
-- This program is free software: you can redistribute it and/or modify
-- it under the terms of the GNU Affero General Public License as published by
-- the Free Software Foundation, either version 3 of the License, or
-- (at your option) any later version.
--
-- This program is distributed in the hope that it will be useful,
-- but WITHOUT ANY WARRANTY; without even the implied warranty of
-- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
-- GNU General Public License for more details.
--
-- You should have received a copy of the GNU Affero General Public License
-- along with this program.  If not, see <http://www.gnu.org/licenses/>.
--

CREATE TABLE IF NOT EXISTS User (
  ID int(11) NOT NULL AUTO_INCREMENT,
  Username varchar(20),
  PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS Projects (
  ID int(11) NOT NULL AUTO_INCREMENT,
  Name varchar(63),
  PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS ProPermission (
  ProjectID int(11) NOT NULL,
  UserID int(11) NOT NULL,
  CONSTRAINT permissionProject_ibfk FOREIGN KEY (ProjectID) REFERENCES Projects (ID) ON UPDATE CASCADE,
  CONSTRAINT permissionUser_ibfk FOREIGN KEY (UserID) REFERENCES User (ID) ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS Bait (
  ID int(11) NOT NULL AUTO_INCREMENT,
  GeneName varchar(25),
  GeneID int(12),
  ProjectID int(11) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT baitProject_ibfk FOREIGN KEY (ProjectID) REFERENCES Projects (ID) ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS Band (
  ID int(11) NOT NULL AUTO_INCREMENT,
  BaitID int(11) NOT NULL,
  Location varchar(100),
  ProjectID int(11) NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT bandBait_ibfk FOREIGN KEY (BaitID) REFERENCES Bait (ID) ON UPDATE CASCADE,
  CONSTRAINT bandProject_ibfk FOREIGN KEY (ProjectID) REFERENCES Projects (ID) ON UPDATE CASCADE
);
