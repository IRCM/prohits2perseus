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

INSERT INTO Bait (ID,GeneName,GeneID,ProjectID)
VALUES (1,'POLR3B',55703,1);
INSERT INTO Bait (ID,GeneName,GeneID,ProjectID)
VALUES (2,'POLR3B-R120S',55703,1);
INSERT INTO Bait (ID,GeneName,GeneID,ProjectID)
VALUES (3,'Flag',null,1);
INSERT INTO Bait (ID,GeneName,GeneID,ProjectID)
VALUES (4,'POLR2A',5431,2);
INSERT INTO Bait (ID,GeneName,GeneID,ProjectID)
VALUES (5,'POLR2A-S34V',5431,2);
INSERT INTO Bait (ID,GeneName,GeneID,ProjectID)
VALUES (6,'EGFP',-1,2);
INSERT INTO Band (ID,BaitID,Location,ProjectID)
VALUES (1,1,'OF_20191011_COU_01',1);
INSERT INTO Band (ID,BaitID,Location,ProjectID)
VALUES (2,1,'OF_20191011_COU_02',1);
INSERT INTO Band (ID,BaitID,Location,ProjectID)
VALUES (3,1,'OF_20191011_COU_03',1);
INSERT INTO Band (ID,BaitID,Location,ProjectID)
VALUES (4,2,'OF_20191011_COU_04',1);
INSERT INTO Band (ID,BaitID,Location,ProjectID)
VALUES (5,2,'OF_20191011_COU_05',1);
INSERT INTO Band (ID,BaitID,Location,ProjectID)
VALUES (6,2,'OF_20191011_COU_06',1);
INSERT INTO Band (ID,BaitID,Location,ProjectID)
VALUES (7,3,'OF_20191011_COU_07',1);
INSERT INTO Band (ID,BaitID,Location,ProjectID)
VALUES (8,3,'OF_20191011_COU_08',1);
INSERT INTO Band (ID,BaitID,Location,ProjectID)
VALUES (9,3,'OF_20191011_COU_09',1);
INSERT INTO Band (ID,BaitID,Location,ProjectID)
VALUES (10,4,'OF_20191105_COU_01',2);
INSERT INTO Band (ID,BaitID,Location,ProjectID)
VALUES (11,5,'OF_20191105_COU_02',2);
INSERT INTO Band (ID,BaitID,Location,ProjectID)
VALUES (12,6,'OF_20191105_COU_03',2);
