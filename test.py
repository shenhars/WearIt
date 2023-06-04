from flask import Flask, request, jsonify
from flask_restful import Api, Resource, reqparse, abort, fields, marshal_with
from itemToDB import *
from perseImg import *
from suitUp import generate_apparel_suggestion
import base64
import io
import random
from PIL import Image

# counter = 0

app = Flask(__name__)

api = Api(app)

def upload_image():
	source_path = "images/image.jpg"  # Path of the image in Firebase Storage
	destination_path = "image.jpg" + counter  # Path where the image will be downloaded locally
	download_image(source_path, destination_path)
	return destination_path
# Example usage
item_counter=0


@app.route('/', methods=['GET', 'POST'])
def handle_request():
	print("connected!")
	return "lets add photos"


@app.route('/parse', methods=('GET', 'POST'))
def parse():
	print("PARSE")
	data = request.get_json(force=True)
	image_b64 = data['image']
	image_data = base64.b64decode(image_b64)
	image = Image.open(io.BytesIO(image_data))
	# image.save('output.png', 'PNG')  # or any format you want
	# image = request.files
	rnd = random.randint(1,1000)
	image.save('./images/image' + str(rnd) + '.jpg')
	item = extract_item_info('./images/image' + str(rnd) + '.jpg')
	add_item_to_db(item, rnd)
	return 'Image uploaded successfully'


@app.route('/generate', methods=('GET', 'POST'))
def generate():
	print("GENERATE")
	# return jsonify("")
	apparel_suggestion = generate_apparel_suggestion()
	list_of_words = apparel_suggestion.split(",")
	# print(list_of_words[1], list_of_words[2])
	shoes = "         " + list_of_words[0] + ' shoes'
	pants = "         " + list_of_words[1] + ' ' + list_of_words[2]
	shirts = "         " + list_of_words[3] + ' ' + list_of_words[4] + '\n    Have a good day!'
	indexs = "    Items ID: " + list_of_words[5] + ', ' + list_of_words[6] + ', ' + \
			 list_of_words[7] + "\n"
	response = {
			'shoes': shoes,
			'pants': pants,
			'shirts': shirts,
			'indexes': indexs
		}
	return jsonify(response)


if __name__ == "__main__":
	app.run(host="0.0.0.0", port=5000, debug=True)