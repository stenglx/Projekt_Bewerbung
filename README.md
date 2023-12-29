# Train a model using build file metrics

- objective is build result 
- features are 5 categories of build file changes extracted via buildDiffer

## data
This folder contains the travistorrent csv file.
For simplicity a smaller subversion of the file is uploaded to git to still enable the user to run the project without having to load the massive file.
In addition preprocessing was done to ensure the presence of all four lables namely failed, errored, passed and canceled. 
(25 commits per build result = 100 commits in total)
There is a subfolder called pom_files which contains the current and the previous (before commit) pom.xml file's version for all commits used in this project. It also contains a csv file with the build result. 

## dataprep  
This folder deals with data preparation for build Differ. 
If has a subfolder called projects which is used for cloning the repo's (in order to get the pom files) - can be ignored by users.
The heart of this part is the dataprep.ipynb notebook which deals with the data and aims to download all pom files needed such that build Differ is able to use them automatically (due to folder structure)

## buildDiffer
This folder contains the java project BuildDiffer which mainly has the purpose to run the build differ tool of Prof. Macho to extract metrics from the build files (build file changes from pom files). The extracted metrics are then saved to a csv file where each row corresponds to 1 commit.

## ML
This folder contains a Jupyter Notebook for dataset splitting, augmentation and training a RandomForst model. 

