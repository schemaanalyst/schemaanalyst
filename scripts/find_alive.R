library(ggplot2)
library(plyr)
library(dplyr)
library(tidyr)
library(stringr)
library(readr)

process <- function(file) {
  read_csv(file) %>% 
    group_by(identifier) %>%
    mutate(id = 1:length(identifier)) %>%
    ungroup() %>%
    filter (type == "NORMAL", killed == "false") %>%
    select(schema,id) %>%
    unique() %>%
    group_by(schema) %>%
    summarise(ids = paste(collapse=",",id)) %>%
    write_csv(paste(sep="",str_replace(file,".dat",""),"-alive.dat"))
}


args <- commandArgs(TRUE)
file <- args[1]
process(file)
#process('/home/abdullah/phd_exper/schemaanalyst-origanal-github/schemaanalyst/results/mutanttiming.dat')
