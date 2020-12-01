from django.urls import path, include

app_name = 'capstone'
urlpatterns = [
    path('', include('rest_framework.urls', namespace='rest_framework_category'))
]