from django.shortcuts import render

# Create your views here.
from rest_framework import viewsets

from capstone.models import Test
from capstone.serializers import TestSerializer


class TestViewSet(viewsets.ModelViewSet):
    queryset = Test.objects.all()
    serializer_class = TestSerializer
