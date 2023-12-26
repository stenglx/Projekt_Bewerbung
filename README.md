# Train a model using build file metrics

- objective is build result 

## data
This folder contains the travistorrent csv file.
For simplicity a smaller subversion of the file is uploaded to git to still enable the user to run the project without having to load the massive file.
There is a subfolder called pom_files which contains the current and the previous (before commit) pom.xml file's version for all commits used in this project. 
## dataprep
This folder deals with data preparation. 
If has a subfolder called projects which is used for cloning the repo's (in order to get the pom files) - can be ignored by users.
The heart of this part is the dataprep.ipynb notebook which deals with the data and aims to download all pom files needed. 

TODO: save pom files to data instead of dataprep
## buildDiffer
This folder contains the class java project BuildDiffer which mainly has the purpose to run the build differ tool of Prof. Macho to extract metrics from the build files (build file changes). The extracted metrics are then saved to a csv file where each row corresponds to 1 commit.

## ML
This folder contains a Jupyter Notebook for dataset splitting and training a RandomForst model. 

## TODO
- data augmentation as most labels are 'errored' and therefore model overfits
- instead maybe shuffle travistorrent dataframe before slicing to only get first 100 commits 
- plots 

- - Plot accuracy over time 


# TODO proposal

- add new literature
- plagcheck to be sure that everything is cited correctly
- specify tools used 
- make ready for presentation 
- make pptx 

