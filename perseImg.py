import test
from google.cloud import vision
import os
import openai
import webcolors
import colorsys
from Item import item
from convert_rgb_to_color import *

items_list = ["top", "shirt", "jeans", "pants", "shoes", "shoe", "shorts", "person"]


# Set your OpenAI API key as an environment variable
os.environ["OPENAI_API_KEY"] = "your openAI api key"

os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = "your google cloud credentials json path"

# Set the API key for OpenAI library
openai.api_key = os.getenv("OPENAI_API_KEY")
openai.organization = "your openAI organization if needed"

# Instantiate the Google Vision client
client = vision.ImageAnnotatorClient()


def extract_item_info(image_path):
    with open(image_path, 'rb') as image_file:
        content = image_file.read()

    image = vision.Image(content=content)

    # Perform object detection
    response = client.object_localization(image=image)
    objects = response.localized_object_annotations

    if objects:
        detected_object = objects[0].name
    else:
        detected_object = "Unknown"

    # Perform image properties analysis for color detection
    response = client.image_properties(image=image)
    properties = response.image_properties_annotation

    if properties.dominant_colors.colors:
        detected_color = properties.dominant_colors.colors[0].color
    else:
        detected_color = "Unknown"

    if (detected_object.lower() not in items_list) or (detected_object == "Unknown") or (detected_color == "Unknown"):
        print(f"Item: {detected_object.lower()} not valid")

    # color_name = parse_rgb_to_string(detected_color.red, detected_color.green, detected_color.blue)
    color_name = (ColorNames.findNearestWebColorName(detected_color.red, detected_color.green, detected_color.blue))
    # print(detected_color.red, detected_color.green, detected_color.blue)
    p = item(detected_object.lower(), color_name.lower())
    return p


if __name__ == '__main__':
    # Example usage: print the detected object and color
    print("Hi")
    image_path = "images/images (1).jpeg"
    pp = extract_item_info(image_path)
    # print("red:\n", pp.color.red)

    # print(color_name)
    print("Detected Object:", pp.item_type)
    print("Detected Color:", pp.color)
