from sklearn.feature_extraction.text import CountVectorizer
from sklearn import svm
import pandas as pd
from bs4 import BeautifulSoup
from nltk.stem.porter import *
from nltk.corpus import stopwords
import re
from util import *

class SentimentAnalyzer:
    def set_train_data(self, train):
        self.raw_train = train
        self.clean_train = self.clean_data(self.raw_train)

    def set_test_data(self, test):
        self.raw_test = test
        self.clean_test = self.clean_data(self.raw_test)

    def clean_text(self, raw_text):
        cleaned_text = BeautifulSoup(raw_text).get_text()
        letters_only = re.sub("[^a-zA-Z]", " ", cleaned_text)
        words = letters_only.lower().split()
        #stemmer = PorterStemmer()
        #stemmed_words = [stemmer.stem(w) for w in words]
        return (" ".join(words))

    def clean_data(self, raw_data):
        num_reviews = raw_data["review"].size
        clean_data = []
        i = 0
        for text in raw_data["review"]:
            if ((i + 1) % 1000 == 0):
                print("Review " + str(i + 1) + " of " + str(num_reviews))
            i += 1
            clean_data.append(self.clean_text(text))

        return clean_data

    def extract_ngram_features(self, min_n, max_n, max_num_features):
        print("Extracting ngram features...")
        self.vectorizer = CountVectorizer(analyzer = "word", \
                                    tokenizer = None, \
                                    preprocessor = None, \
                                    stop_words = stopwords.words("english"), \
                                    max_features = max_num_features, \
                                    ngram_range = (min_n, max_n))
        # transforms the training data to feature vectors
        self.feature_vectors = self.vectorizer.fit_transform(self.clean_train)
        print("Extraing ngram feautres done...")

    def train_classifier(self):
        print ("Training classifier...")
        self.classifier = svm.SVC(probability = True)
        self.classifier = self.classifier.fit(self.feature_vectors, self.raw_train["sentiment"])
        print(self.classifier.score(self.feature_vectors, self.raw_train["sentiment"]))
        print("Training classifier done...")

    def test_classifier(self):
        print ("Testing classifier...")
        test_feature_vectors = self.vectorizer.transform(self.clean_test)
        print (self.classifier.score(test_feature_vectors, self.raw_test["sentiment"]))
        print ("Testing classifier done...")

    def classify(self, text):
        text_array = [self.clean_text(text)]
        text_feature_vector = self.vectorizer.transform(text_array)[0]
        return self.classifier.predict(text_feature_vector)

    def probability(self, text):
        text_array = [self.clean_text(text)]
        text_feature_vector = self.vectorizer.transform(text_array)[0]
        return self.classifier.predict_proba(text_feature_vector)

"""
data = pd.read_csv("../data/sentiment/labeledTrainData.tsv", header=0, delimiter="\t", quoting=3)
train = data[:][:25000]
#test = data[:][20000:25000]
analyzer = SentimentAnalyzer()
analyzer.set_train_data(train)
#analyzer.set_test_data(test)
analyzer.extract_ngram_features(1, 1, 5000)
analyzer.train_classifier()
#analyzer.test_classifier()
"""

analyzer = load_object("../data/sentiment/models/sentiment.pkl")

print(analyzer.probability("horrible boring disgusting"))



