import firebase_admin
import test
from firebase_admin import credentials
from firebase_admin import firestore
from firebase_admin import storage
from perseImg import *
from Item import *
from firebase_init import db, storage_client


items_dic = {"top": "shirt","person": "shirt", "shirt": "shirt", "jeans": "pants",
             "pants": "pants", "shorts":"pants", "shoes": "shoes", "shoe": "shoes"}

def add_item_to_db(item,ind=0):
    # Create a new document with auto-generated ID

    doc_ref = db.collection(items_dic[item.item_type]).document()
    # Set the product data in the document
    doc_ref.set({
        'item_index': ind,
        'item_type': item.item_type,
        'item_color': item.color
    })

if __name__ == "__main__":
    items_dic = {"top": "shirt", "shirt": "shirt", "jeans": "pants",
                 "pants": "pants", "shorts": "pants", "shoes": "shoes",
                 "shoe": "shoes"}

    # Initialize Firebase app
    cred = credentials.Certificate("your firebase credentials json path")
    firebase_admin.initialize_app(cred)

    storage_client = storage.bucket()

    # Get the Firestore database reference
    db = firestore.client()

