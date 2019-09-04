import pandas as pd
import numpy as np
import csv
from sklearn.model_selection import train_test_split
#from sklearn.metrics import accuracy_score
from sklearn.metrics import *
#from sklearn.metrics import f1_score
from sklearn.svm import SVC
from sklearn.svm import LinearSVC
from sklearn import tree
from sklearn.neural_network import MLPClassifier
import sys
import matplotlib.pyplot as plt

# help: https://www.kaggle.com/ahmethamzaemra/mlpclassifier-example

df = pd.read_csv("/Users/TrentWoods/Desktop/data (1).csv")


# header
columns_list = ['sport', 'dport', 'proto', 'flow_id', 'count', 'len', 'ttl', 'time', 'label']
df.columns = columns_list

# print(df['sport'][0])dkk
# features to use in teaching
features = ['sport', 'dport', 'proto', 'count', 'len', 'ttl', 'time']

# X data is used to predict, y is answer
X = df[features]
y = df['label']

tree_acc = list()
neural_acc = list()
SVC_acc = list()

acc_scores = 0
for i in range(0, 10):
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.25)

    #Decision Trees
    clf_tree = tree.DecisionTreeClassifier()
    clf_tree.fit(X_train, y_train)
    # f1scoreDT = f1_score(X_test, y_test)


    # Neural network (Multilayer Perceptron Classifier)
    clf_neural = MLPClassifier()
    clf_neural.fit(X_train, y_train)

    #SVC's
    clf_SVC = SVC(gamma='auto')     #SVC USE THIS
    #clf = LinearSVC()  #Linear SVC
    clf_SVC.fit(X_train, y_train)


    #here you are supposed to calculate the evaluation measures indicated in the project proposal (accuracy, F-score etc)
    result_tree = clf_tree.score(X_test, y_test)  #accuracy score
    result_neural = clf_neural.score(X_test, y_test)  #accuracy score
    result_SVC = clf_SVC.score(X_test, y_test)  #accuracy score
       
    
    print('Decision tree result: ', i, ' ',  result_tree)
    print('Neural Network result: ', i, ' ', result_neural)
    print('SVC Result: ', i, ' ',  result_SVC)

    tree_acc.append(result_tree)
    neural_acc.append(result_neural)
    SVC_acc.append(result_SVC)


plt.figure(1)
ind = np.arange(len(tree_acc))   
width = 0.25

print(tree_acc)
p1 = plt.bar(ind, tree_acc, width)
p2 = plt.bar(ind, neural_acc, width)
p3 = plt.bar(ind, SVC_acc, width)

plt.ylabel('Accuracy')
plt.xlabel('Executions')
plt.title('Accuracy Scores for Different ML Algorithms')
plt.xticks(ind, ('1', '2', '3', '4', '5', '6', '7', '8', '9', '10'))
plt.yticks(np.arange(0, 1.2, step=0.2))
plt.legend((p1[0], p2[0], p3[0]), ('Decision Tree', 'Neural Network', 'SVC'))
plt.tight_layout()


#plt.figure(2)
#df.sport.value_counts()[:10].plot(kind = 'bar', title = 'Top 10 Source Port frequencies')
#df.groupby('label')['sport'].value_counts().head().plot(kind = 'bar')
#df.groupby('label')['sport'].nlargest(3).head().plot(kind = 'bar')
#print(df.groupby('label')['sport'].value_counts())
#array = df.groupby('label')['sport'].value_counts()[:3]
plt.subplot(221)
plt.title("Label = 1")
plt.xlabel('Source Port #')
plt.ylabel('Frequency')

array1 = df.query('label == 1')
array1['sport'].value_counts()[:3].plot(kind = 'bar')

plt.subplot(222)
plt.title("Label = 2")
plt.xlabel('Source Port #')
plt.ylabel('Frequency')
array2 = df.query('label == 2')
array2['sport'].value_counts()[:3].plot(kind = 'bar')

plt.subplot(223)
plt.title("Label = 3")
plt.xlabel('Source Port #')
plt.ylabel('Frequency')
array3 = df.query('label == 3')
array3['sport'].value_counts()[:3].plot(kind = 'bar')

plt.subplot(224)
plt.title("Label = 4")
plt.xlabel('Source Port #')
plt.ylabel('Frequency')
array4 = df.query('label == 4')
array4['sport'].value_counts()[:3].plot(kind = 'bar')

plt.tight_layout()

# destination port plot
plt.figure(3)

plt.subplot(221)
plt.title("Label = 1")
plt.xlabel('Destination Port #')
plt.ylabel('Frequency')

array1 = df.query('label == 1')
array1['dport'].value_counts()[:3].plot(kind = 'bar')

plt.subplot(222)
plt.title("Label = 2")
plt.xlabel('Destination Port #')
plt.ylabel('Frequency')
array2 = df.query('label == 2')
array2['dport'].value_counts()[:3].plot(kind = 'bar')

plt.subplot(223)
plt.title("Label = 3")
plt.xlabel('Destination Port #')
plt.ylabel('Frequency')
array3 = df.query('label == 3')
array3['dport'].value_counts()[:3].plot(kind = 'bar')

plt.subplot(224)
plt.title("Label = 4")
plt.xlabel('Destination Port #')
plt.ylabel('Frequency')
array4 = df.query('label == 4')
array4['dport'].value_counts()[:3].plot(kind = 'bar')

plt.tight_layout()


# distribution of protocol # per label #
plt.figure(4)
plt.ylabel('Frequency')
df.groupby('label')['proto'].value_counts().plot(kind = 'bar', title = 'Protocol # in relation to Label #')
plt.xlabel('Label #, Protocol #')
plt.tight_layout()

# mean TTL per label
plt.figure(5)
plt.ylabel('Average TTL')
df.groupby('label')['ttl'].agg(np.mean).plot(kind = 'bar', title = 'Average TTL for Each Label')
plt.xlabel('Label #')
plt.tight_layout()

# mean count rate per label
plt.figure(6)
df.groupby('label')['count'].agg(np.mean).plot(kind = 'bar', title = 'Average Count # for each Label')
plt.xlabel('Label #')
plt.ylabel('Average Count')
plt.tight_layout()

# mean length per label
plt.figure(7)
df.groupby('label')['len'].agg(np.mean).plot(kind = 'bar', title = 'Average length for each Label')
plt.xlabel('Label #')
plt.ylabel('Average Length')
plt.tight_layout()

# mean time per label
plt.figure(8)
df.groupby('label')['time'].agg(np.mean).plot(kind = 'bar', title = 'Average time for each Label')
plt.xlabel('Label #')
plt.ylabel('Average Time')
plt.tight_layout()




plt.show()

# X_train is the training data we are using ( all of the columns)
# y_train is the a 2 column array 
# X_test is 201 rows of all of the columns
# y_test is 201 rows of 2 columns




#sys.exit()