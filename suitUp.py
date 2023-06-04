
import random
import openai
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
import os
from firebase_init import db, storage_client


MAX_ATTEMPTS = 1

os.environ["OPENAI_API_KEY"] = "your openAI key"
openai.organization = "your openAI organization if needed"

openai.api_key = os.getenv("OPENAI_API_KEY")



def get_all_shoes():
    shoes = db.collection('shoes').get()
    ret = []
    for shoe in shoes:
        ret.append("index: "+ str(shoe.get('item_index')) + ' color: ' + shoe.get('item_color') + ' ')
    return ret


def get_all_pants():
    pants = db.collection('pants').get()
    ret = []
    for pant in pants:
        ret.append("index: "+ str(pant.get('item_index'))+' item_color: '+pant.get('item_color')+'type: '+pant.get('item_type') + ' ')
    return ret


def get_all_shirts():
    shirts = db.collection('shirt').get()
    ret = []
    for shirt in shirts:
        ret.append("index: "+str(shirt.get('item_index'))+' item_color: '+shirt.get('item_color')+'type: '+shirt.get('item_type') + ' ')
    return ret


def get_vals():
    return [get_all_shoes(), get_all_pants(), get_all_shirts()]


def generate_apparel_suggestion():
    attempt = 0

    return gpt3_color_matching_model()





def gpt3_color_matching_model(purpose="running"):
    vals = get_vals()
    prompt = f"shoes: {vals[0]}, pants: {vals[1]}, shirts: {vals[2]}" \
             f". i want a good suitable clothing to going to {purpose} please" \
             f" generate me a good match return me the answer in shape of: " \
             "{shoes_color},{pants_color},{pants_type},{shirt_color},{shirt_type},{shoe_index},{pant_index},{shirt_index}" \
             f" without any other sign or word!!! make sure that the format is exactly:" \
             "{shoes_color},{pants_color},{pants_type},{shirt_color},{shirt_type},{shoe_index},{pant_index},{shirt_index}"
    # prompt = f"Is {color1} a good match for {color2} in fashion?"
    # print("prompt:", prompt)
    response = openai.Completion.create(
        model="text-davinci-003",
        prompt=prompt,
        temperature=0.8,
        max_tokens=30,
        top_p=1.0,
        frequency_penalty=0.0,
        presence_penalty=0.0,
        stop=None,
        n=1
    )

    choice = response.choices[0].text.strip().lower()

    return choice