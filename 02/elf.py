#!/usr/bin/python3

OPONENT_ROCK = "A"
OPONENT_PAPER = "B"
OPONENT_SCISSOR = "C"

MY_ROCK = "X"
MY_PAPER = "Y"
MY_SCISSOR = "Z"

outcome = {
    OPONENT_ROCK: {
        MY_ROCK: 3, MY_PAPER: 6, MY_SCISSOR: 0
    } , 
    OPONENT_PAPER: {
        MY_ROCK: 0, MY_PAPER: 3, MY_SCISSOR: 6
    } , 
    OPONENT_SCISSOR : {
        MY_ROCK: 6, MY_PAPER: 0, MY_SCISSOR: 3
    } 
}

granted_per_shape = {
    MY_ROCK: 1,
    MY_PAPER: 2,
    MY_SCISSOR: 3
}

with open('input.txt') as f:
    total_score = 0;
    for line in f:
        (his, mine) = line.strip().split(" ")
        score = outcome[his][mine] + granted_per_shape[mine]
        total_score += score
        #print('Line: {} score: {}'.format(line.strip(), score))
    
    print('Final score: {}'.format(total_score))
