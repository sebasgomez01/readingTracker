from django.urls import path
from .views import create_book
from . import views 

urlpatterns = [
	path("", views.index, name="index"),
	path("holaLoco", views.sayHiLoco, name="helloLoco"),
	path('create_book/', views.create_book, name='create_book'),
	path('<int:book_id>', views.delete_book,  name='delete_book'),
	path('update/<int:book_id>', views.update_read,  name='update_read'),
	path('new_user_register', views.new_user_register,  name='new_user_register'),
	path('log_in_user', views.log_in_user,  name='log_in_user')
]

#curl -X POST -H "Content-Type: application/json" -d '{"title": "Los Hermanos Karamazov", "author": "Fiodor Dostoyevski", "pages": 1124, "read": False }' http://localhost:8000/backEnd/create_book/
