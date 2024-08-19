from django.shortcuts import render

# Create your views here.

from django.http import HttpResponse
from django.http import JsonResponse
from .models import BookInfo
from django.views.decorators.http import require_POST
from django.views.decorators.csrf import csrf_exempt
import json
from django.utils import timezone
from django.views.decorators.http import require_http_methods
from django.contrib.auth.models import User
from django.contrib.auth import authenticate, login
from django.contrib.auth.models import AnonymousUser

collectionItems = [
	{
		'title': "El Hobbit",
		'auhtor': "J. R. R. Tolkien",
		'pages': 121,
		'read': True
	},
	{
		'title': "Los Hermanos Karamazov",
		'auhtor': "Fiodor Dostoyevski",
		'pages': 1131,
		'read': False
	}
]

def index(request):
	return HttpResponse("Hello Sebastian,´i'm talking to you from the server")

def sayHiLoco(request):
	return HttpResponse("Hello Loco, are you insane?")


# views.py


@csrf_exempt  # Necesario solo si estás usando CSRF protection
@require_POST  # Solo permite solicitudes POST
def create_book(request):
    # Primero chequeo si existe algún usuario autenticado:
    actual_user = request.user
    print(actual_user)
    print(request.session)
    print(request.COOKIES)
    
    if actual_user.is_authenticated:
        owner = actual_user.username
    else:
        owner = 'no one'

    # Obtengo los datos de la solicitud POST
    data = json.loads(request.body.decode('utf-8'))

    # Extrae los parámetros de la solicitud
    title = data.get('title')
    author = data.get('author')
    pages = data.get('pages')
    read = data.get('read')
    date_pub = timezone.now()

    # Crea una nueva instancia del modelo y la guarda en la base de datos
    new_book = BookInfo(title=title, author=author, pages=pages, read=read, date_pub=date_pub, owner=owner)
    new_book.save()

    # Necesito devolver el id en la respuesta para guardarlo en el frontend, de este modo 
    # puedo eliminar un ítem por su id que es único, y manejar el problema de cuando tengo 
    # más de un libro con los mismos datos, entonces elimino por id
    id = new_book.id

    # Devuelvo un json con un mensaje que informa que el libro fue creado y el id que le propociono
    # la base de datos
    return JsonResponse({'message': 'Libro creado correctamente', 'id': id})


@csrf_exempt
#@require_DELETE 
@require_http_methods(["DELETE"])
def delete_book(request, book_id):
	#try:
	bookToDelete = BookInfo.objects.get(id=book_id)
	bookToDelete.delete()
	return JsonResponse({'message': f'Libro con el id {book_id} eliminado correctamente'})
    #except BookInfo.DoesNotExist:
    #    return JsonResponse({'error': f'Libro con el id {book_id} no encontrado'}, status=404)
    #except Exception as e:
    #    return JsonResponse({'error': f'Error al eliminar el libro: {str(e)}'}, status=500

@csrf_exempt
@require_http_methods(["PATCH"])
def update_read(request, book_id):
    try:
        bookToUpdate = BookInfo.objects.get(id=book_id)
        # Actualizo read 
        print(bookToUpdate.read)
        bookToUpdate.read = not(bookToUpdate.read)
        print(bookToUpdate.read)
        # lo guardo en la base de datos (no sé si es necesario)
        bookToUpdate.save() # ESTA PUTA VARIABLE ME ARRUINABA EL MÉTODO PATCH, YA LO ARREGLE

        return JsonResponse({'message': f'Libro con el id {book_id} actualizado correctamente'})

    except BookInfo.DoesNotExist:
        return JsonResponse({'error': f'Libro con el id {book_id} no encontrado'}, status=404)

    except Exception as e:
        return JsonResponse({'error': f'Error al actualizar el libro: {str(e)}'}, status=500)



@csrf_exempt  # Necesario solo si estás usando CSRF protection
@require_POST  # Solo permite solicitudes POST
def new_user_register(request):
    # Obtiene los datos de la solicitud POST
    data = json.loads(request.body.decode('utf-8'))

    # Obtengo los datos del usuario 
    username = data.get('username')
    password = data.get('password')
    email = data.get('email')

    # Verifico si el usuario ya existe
    if User.objects.filter(username=username).exists():
        #messages.error(request, 'Este nombre de usuario ya está en uso. Por favor, elige otro.')
        return JsonResponse({'error': 'yes', 'message': 'Username already in use'}) 
        # redirect('crear_usuario')  # Puedes cambiar 'crear_usuario' al nombre de tu vista

    # Verifico si el email ya existe
    if User.objects.filter(email=email).exists():
        #messages.error(request, 'Este email ya se encuantra asociado a un usuario. Por favor, elige otro o ingresa en tu cuenta.')
        return JsonResponse({'error': 'yes', 'message': 'Email already in use'})  
        # redirect('crear_usuario')  # Puedes cambiar 'crear_usuario' al nombre de tu vista

    # Creo el nuevo usuario y lo guardo en la base de datos 
    new_user = User.objects.create_user(username=username, password=password, email=email)

    return JsonResponse({'error': 'no', 'message': 'usuario creado correctamente'})


@csrf_exempt  # Necesario solo si estás usando CSRF protection
@require_POST  # Solo permite solicitudes POST
def log_in_user(request):

    # Obtengo los datos de la solicitud
    data = json.loads(request.body.decode('utf-8'))

    # printeo los cookies y las sessions
    print(request.session)
    print(request.COOKIES)

    # Obtengo los datos del usuario 
    username = data.get('username')
    print(username)
    password = data.get('password')
    print(password)

    # Chequeo si las credenciales son correctas:
    user = authenticate(request, username=username, password=password)
    print(user)

    if user is not None:
        print(request.user)
        login(request, user)
        print(request.user)
        return JsonResponse({'message': 'usuario loggeado correctamente'})
    else: 
        return JsonResponse({'message': 'Error en el login: credenciales incorrectas'})