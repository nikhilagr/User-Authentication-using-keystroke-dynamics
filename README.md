# User Authentication Using Keystroke Dynamics

The unique typing patterns of users can be used as a signature to identify genuine users from imposters. Keystroke signature fuses the simplicity of passwords with increased reliability from biometrics. Unlike other biometrics like IRIS, face, fingerprints etc which need special hardware infrastructure, keystroke biometrics are economical to implement and can be easily integrated into the existing computer security systems. They help augment the existing security infrastructure by being part of a multilevel authentication system. Using Machine learning algorithms, a high accuracy rate in detecting imposters has been demonstrated in this project. The machine learning model is trained with the typing patterns of the subjects. Then it is provided with test data with patterns from the subject as well as from imposters posing as the subject. The model demonstrates the ability to discern genuine users from imposters based on the test pattern’s similarity to the trained model for the subject.

### Data Collection
**There was't any dataset present of keystroke patterns so build standalone desktop application to collect keystrokes from users. The create a typing pattern used calculated 3 different type of timings
for each key pressed.**
  * **Hold time** – time between press and  release of a key.
  * **Keydown-Keydown time** – time between the pressing of consecutive keys.
  * **Keyup-Keydown time** – time between the release of one key and the press of next key.
  <img src = "Images/Keytimings.png">
  
  <img src = "http://g.recordit.co/B6qfWgeBup.gif" >
  
  **Follwed above procedure and collected data from 15 different users**
### Feature selection
For the password typed for each key pressed measured hold time, Keydown-Keydown time,Keyup-Keydown time in milliseconds and this were considered as features.

 <img src= "Images/Data.png">
 
### Training Machine Learning Models.
As the problem is classification of user whether the user is genuine or imposter, supervised learning algorithms like logistic regression, support vector machine and K-nearest-neighbour were used.Used sklearn libraries for training the models.

### Test & evaluate the ML Model
* Train Test Split, where the dataset is divided into two different datasets, training set and testing set.Train the data with training set and test the data on testing set and evaluate the accuracy of the model
* Cross Validation: Split dataset into K equal partitions(folds), Use partition 1 as testing set and remaining K-1 partitions as training set and calculate accuracy. Repeat the process K number of times and use average of all the accuracies to evaluate model performance.

### Results
<img src="Images/Result.png">

<img src= "Images/Confusion.png">

### Refrences
* https://ieeexplore.ieee.org/abstract/document/5270346

