import firebase_admin
from firebase_admin import credentials, firestore, storage

cred = credentials.Certificate(
    "your firebase credentials json path")

app = firebase_admin.initialize_app(cred, {
    'storageBucket': 'your firebase project name',
})

storage_client = storage.bucket(app=app)
db = firestore.client()
