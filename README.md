# ML-Project
User Authentication using Keystroke dynamics


User Authentication using keystroke dynamics.
Jan-2018 to May-2018

Subject: Machine Learning
Technology stack: Python | Java | IBM Watson Machine Learning Service.
Tools: IntelliJ Idea | Jupyter Notebook.

One line Description: Using keystroke dynamics unique unique typing patterns of users can be used as signature to authenticate user and classify user as genuine or imposter. 

Detailed description: 
The project can be divided into four stages.
1)Data collection and formation of typing pattern.
Created standalone desktop application using java to capture typing biometrics. Typing pattern consists of hold time, keydown keydown time , keyup keydown time.Hold time is time between press and release of the key, Keyup keydown time is time between pressing of consecutive keys and Keyup-Keydown time is time between release of one key and press of next key. Using these three parameters a pattern is formed which is unique for each user. 
2)Selection of ML Algorithm and training the model.
As the problem is classification of user whether the user is genuine or imposter, supervised learning algorithms like logistic regression, support vector machine and K-nearest-neighbour were used.Used sklearn libraries for training the models.
3)Test & evaluate the ML Model.
 The models are evaluated using two techniques,
a)Train Test Split, where the dataset is divided into two different datasets, training set and testing set.Train the data with training set and test the data on testing set and evaluate the accuracy of the model.
b)Cross Validation: Split dataset into K equal partitions(folds), Use partition 1 as testing set and remaining K-1 partitions as training set and calculate accuracy. Repeat the process K number of times and use average of all the accuracies to evaluate model performance.
4)Deployment. 
 The logistic regression model was deployed as a service through IBM Watson.
