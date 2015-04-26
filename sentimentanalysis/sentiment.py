from sklearn.feature_extraction.text import CountVectorizer
from sklearn import svm
import pandas as pd
from bs4 import BeautifulSoup
from nltk.stem.porter import *
from nltk.corpus import stopwords
import re

class SentimentAnalyzer:
    def set_train_data(self, dir):
        self.raw_train = pd.read_csv(dir, header=0, delimiter="\t", quoting=3)
        self.clean_train = self.clean_data(self.raw_train)

    def clean_text(self, raw_text):
        cleaned_text = BeautifulSoup(raw_text).get_text()
        letters_only = re.sub("[^a-zA-Z]", " ", cleaned_text)
        words = letters_only.lower().split()
        stemmer = PorterStemmer()
        stemmed_words = [stemmer.stem(w) for w in words]
        return (" ".join(stemmed_words))

    def clean_data(self, raw_train):
        num_reviews = raw_train["review"].size
        clean_train_reviews = []
        for i in range(0, num_reviews):
            if ((i + 1) % 1000 == 0):
                print("Review " + str(i + 1) + " of " + str(num_reviews))
            clean_train_reviews.append(self.clean_text(raw_train["review"][i]))

        return clean_train_reviews

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
        self.classifier = svm.SVC()
        self.classifier = self.classifier.fit(self.feature_vectors, self.raw_train["sentiment"])
        print(self.classifier.score(self.feature_vectors, self.raw_train["sentiment"]))
        print("Training classifier done...")

    def classify(self, text):
        text_array = [self.clean_text(text)]
        text_feature_vector = self.vectorizer.transform(text_array)[0]
        return self.classifier.predict(text_feature_vector)

    def probability(self, text):
        text_array = [self.clean_text(text)]
        text_feature_vector = self.vectorizer.transform(text_array)[0]
        return self.classifier.predict_proba(text_feature_vector)

analyzer = SentimentAnalyzer()
analyzer.set_train_data("../data/sentiment/labeledTrainData.tsv")
analyzer.extract_ngram_features(1, 1, 5000)
analyzer.train_classifier()

print(analyzer.probability("this movie was really horrible"))


