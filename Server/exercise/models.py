from django.db import models

# Create your models here.
class User(models.Model):
    name=models.CharField(max_length=100)
    gender=models.CharField(max_length=30)
    age=models.IntegerField(default=0)
    weight=models.IntegerField(default=0)
    goal=models.IntegerField(default=0)


def __str__(self):
    return self.name