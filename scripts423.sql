-- получить информацию обо всех студентах (достаточно получить только имя и возраст студента)
-- школы Хогвартс вместе с названиями факультетов

select student.name, student.age, faculty.name
from student
inner join faculty
on student.faculty_id = faculty.id;

-- чтобы получить только тех студентов, у которых есть аватарки

select student.*
from student
inner join avatar
on avatar.student_id = student.id;