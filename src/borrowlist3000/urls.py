"""borrowlist3000 URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.urls import path, include
from django.shortcuts import redirect

urlpatterns = [
    path("api/", include("borrowlist3000_api.urls")),
    # app/… urls are actually served via a special middleware.
    # This route serves as an indication that something went wrong with that middleware
    path("app/", include("borrowlist3000_frontend.urls")),
    path("", lambda request: redirect("/app"))
]
