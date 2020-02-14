--
-- Copyright (c) 2020 Institut de recherches cliniques de Montreal (IRCM)
--
-- This program is free software: you can redistribute it and/or modify
-- it under the terms of the GNU General Public License as published by
-- the Free Software Foundation, either version 3 of the License, or
-- (at your option) any later version.
--
-- This program is distributed in the hope that it will be useful,
-- but WITHOUT ANY WARRANTY; without even the implied warranty of
-- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
-- GNU General Public License for more details.
--
-- You should have received a copy of the GNU General Public License
-- along with this program. If not, see <http://www.gnu.org/licenses/>.
--

INSERT INTO User (ID,Username)
VALUES (1,'poitrasc');
INSERT INTO User (ID,Username)
VALUES (2,'smithj');
INSERT INTO Projects (ID,Name)
VALUES (1,'Flag');
INSERT INTO Projects (ID,Name)
VALUES (2,'BioId');
INSERT INTO ProPermission (ProjectID,UserID)
VALUES (1,1);
INSERT INTO ProPermission (ProjectID,UserID)
VALUES (2,1);
INSERT INTO ProPermission (ProjectID,UserID)
VALUES (1,2);
