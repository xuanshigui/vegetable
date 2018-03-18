
# coding: utf-8

# In[24]:

def readDataSet(path):
    import numpy  as np
    import pandas as pd
    import sklearn.preprocessing as preprocessing
    df = pd.read_csv(path)
    scaler = preprocessing.StandardScaler()
    df['high_6_scaled'] = scaler.fit_transform(df['high_6'])
    df['low_6_scaled'] = scaler.fit_transform(df['low_6'])
    df['high_7_scaled'] = scaler.fit_transform(df['high_7'])
    df['low_7_scaled'] = scaler.fit_transform(df['low_7'])
    df['high_8_scaled'] = scaler.fit_transform(df['high_8'])
    df['low_8_scaled'] = scaler.fit_transform(df['low_8'])
    df['high_9_scaled'] = scaler.fit_transform(df['high_9'])
    df['low_9_scaled'] = scaler.fit_transform(df['low_9'])
    df['high_10_scaled'] = scaler.fit_transform(df['high_10'])
    df['low_10_scaled'] = scaler.fit_transform(df['low_10'])
    #时间计数
    df['DOLower1_scaled'] = scaler.fit_transform(df['DOLower1'])
    df['DOLower3_scaled'] = scaler.fit_transform(df['DOLower3'])
    df.drop(['DOLower1','DOLower3','deviceId','high_6','low_6','high_7','low_7','high_8','low_8','high_9','low_9','high_10','low_10'], axis=1, inplace=True)
    return df
def crossVali(seeds,df):
    import matplotlib.pyplot as plt
    from sklearn.cross_validation import train_test_split
    from sklearn.ensemble import  AdaBoostClassifier
    from sklearn.metrics import classification_report
    from sklearn.tree import DecisionTreeClassifier 
    length = len(seeds)
    precision = 0.0
    recall = 0.0
    f1_score = 0.0
    for seed in seeds:
        X_train_do,X_test_do,y_train_do,y_test_do=train_test_split(df[['high_6_scaled','low_6_scaled','high_7_scaled','low_7_scaled','high_8_scaled','low_8_scaled','high_9_scaled','low_9_scaled','high_10_scaled','low_10_scaled','DOLower3_scaled','DOLower1_scaled']],df[[u'class']],test_size=0.25,random_state=seed)
        clf = DecisionTreeClassifier() 
        n_estimators=400
        learning_rate=1
        ada_discrete=AdaBoostClassifier(base_estimator=clf,learning_rate=learning_rate,n_estimators=n_estimators,algorithm='SAMME')
        ada_discrete.fit(X_train_do,y_train_do)
        predictions_all = ada_discrete.predict(X_test_do)
        result = classification_report(y_test_do,predictions_all).split('\n')
        itemList = result[5].split('       ')
        numList = itemList[1].split('      ')
        precision = precision + float(numList[0])
        recall = recall + float(numList[1])
        f1_score = f1_score + float(numList[2])
    final = str(precision/length)+','+ str(recall/length)+','+str(f1_score/length)
    return final
def do_catch(path,seedStr):
    seedList = seedStr.split(',')
    seeds = []
    for i in seedList:
        seeds.append(int(i))
    df = readDataSet(path)
    result = crossVali(seeds,df)
    return result


#seedStr='0,5,1,12,66,60'
#path = 'C:\\Users\\Administrator\\Desktop\\csv\\tmp\\DOYield.csv'
#do_catch(path,seedStr)

