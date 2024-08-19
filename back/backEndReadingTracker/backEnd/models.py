from django.db import models

# Create your models here.

class BookInfo(models.Model):
	title = models.CharField(max_length=200)
	author = models.CharField(max_length=200)
	pages = models.IntegerField(default=0)
	read = models.BooleanField(default=False)
	date_pub = models.DateTimeField("Date added")
	owner = models.CharField(max_length=200, default="no one")

	def __str__(self):
		return self.title