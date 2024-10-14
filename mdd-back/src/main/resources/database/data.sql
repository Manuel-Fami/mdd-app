INSERT INTO TOPICS (id, title, description)
SELECT * FROM (
    SELECT 1 AS id, 'Visuels' AS title, 'Thème lié aux librairies associées aux visuels, Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris.
' AS description UNION ALL
    SELECT 2, 'Architectures', 'Thème lié aux librairies associées aux architectures, Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris.
' UNION ALL
    SELECT 3, 'Tests', 'Thème lié aux librairies associées aux tests' UNION ALL
    SELECT 4, 'Paradigmes', 'Thème lié aux librairies associées aux paradigmes de programmation, Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris.
' UNION ALL
    SELECT 5, 'Angular', 'Thème lié à Angular, Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris.
' UNION ALL
    SELECT 6, 'Spring boot', 'Thème lié à Spring Boot'
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM TOPICS);