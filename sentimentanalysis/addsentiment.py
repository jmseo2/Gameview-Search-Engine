from sentiment import SentimentAnalyzer
from os import listdir
from util import *

def add_sentiment(input_file_path, output_file_path, analyzer):
    fin = open(input_file_path, "r")
    docid = fin.readline()
    url = fin.readline()
    title = fin.readline()
    lines = [line.strip() for line in fin.readlines()]
    body = " ".join(lines) + "\n"
    fin.close()

    if title == "null":
        return

    prob = analyzer.probability(body)[0]
    fout = open(output_file_path, "w")
    fout.write(str(prob[0]) + " " + str(prob[1]))
    fout.close()

analyzer = load_object("../data/sentiment/models/sentiment.pkl")

input_data_path = "../data/reviewonly/"
output_data_path = "../data/sentimentreview/"
files = listdir(input_data_path)

i = 1
for file in files:
    if i % 1000 == 0:
        print (str(i) + " / " + str(len(files)) + " files done")
    add_sentiment(input_data_path + file, output_data_path + file, analyzer)
    i += 1



