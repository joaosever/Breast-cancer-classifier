This classifier presents a graphical interface that will give the option to input a dataset where the data is formated as a set of vectors with parameteres separated by commas where the final digit
is the classification. The classifier then learns the database and when a vector without the classification parameter is inputed, the classifier predicts the classification. A included example is the 
brest cancer database that is provided, there, 10 cancer parameters expressed as numbers with the final classification of malign (1) or benign (0) are inputed for several cancer. If you load that database to the 
classifier after running Classifiying_interface.java you can then input a vector such as 0,1,1,0,0,0,0,0,0,0 (with 10 parameters) and it will return (1) if it predicts malign of (0) if predicts benign. These classifier
works by turning the data into a graph and making use of a markov random field tree with specific weights.
