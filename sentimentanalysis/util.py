import pickle

def save_object(file_path, obj):
    with open(file_path, 'wb') as output:
        pickle.dump(obj, output, pickle.HIGHEST_PROTOCOL)

def load_object(file_path):
    with open(file_path, 'rb') as input:
        obj = pickle.load(input)
    return obj



