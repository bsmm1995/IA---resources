import pandas as pd
from sklearn.preprocessing import StandardScaler
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LogisticRegression

dataset = pd.read_csv('C:/data.csv')
X = dataset.iloc[:, [0,1,2,3,4,5,6,7,8,9,10]].values
y = dataset.iloc[:, 11].values


X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.25)
sc = StandardScaler()
X_train = sc.fit_transform(X_train)
X_test = sc.transform(X_test)

lr = LogisticRegression()
lr.fit(X_train,y_train)
y_pred = lr.predict(X_test)

from sklearn.metrics import confusion_matrix, precision_score, accuracy_score, recall_score, f1_score, roc_auc_score
matriz = confusion_matrix(y_test,y_pred)
print("Logistic Regression")
print("Matrix Confusion")
print(matriz)

precision = precision_score(y_test,y_pred)
print("Precision: ", precision)

exactitud = accuracy_score(y_test,y_pred)
print("Accuracy: ", exactitud)

sensibilidad = recall_score(y_test, y_pred)
print("Sensitivity: ",sensibilidad)

f1 = f1_score(y_test, y_pred)
print("F1 score: ", f1)

roc = roc_auc_score(y_test, y_pred)
print("ROC AUC score: ", roc)