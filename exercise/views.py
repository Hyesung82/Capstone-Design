from django.shortcuts import render
from django.http import HttpResponseRedirect
from django.urls import reverse
from .models import User
# Create your views here.


def index(request):
    return render(request,'index.html')

def input_form(request):
    return render(request,'register.html')
    
def register(request):
     #post형식 데이터 받아오기
    input_name = request.POST['name']
    input_age = request.POST['age']
    input_gender = request.POST['gender']
    input_weight = request.POST['weight']
    input_goal = request.POST['goal']
    qs = {'name' : input_name , 'age':input_age, 'gender' : input_gender, 'weight':input_weight,'goal':input_goal}
    return render(request,'result.html',qs)

def sets(request):
    return render(request,'set.html')

def test(request):
    return render(request,'test.html')
