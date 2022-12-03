#!/usr/bin/env python3

OPONENT_ROCK = "A"
OPONENT_PAPER = "B"
OPONENT_SCISSOR = "C"

MY_ROCK = "X"
MY_PAPER = "Y"
MY_SCISSOR = "Z"

NEED_LOSE = "X"
NEED_DRAW = "Y"
NEED_WIN = "Z"

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

cheating_code = {
    OPONENT_ROCK: {
        NEED_LOSE: MY_SCISSOR, 
        NEED_DRAW: MY_ROCK, 
        NEED_WIN: MY_PAPER
    } , 
    OPONENT_PAPER: {
        NEED_LOSE: MY_ROCK, 
        NEED_DRAW: MY_PAPER, 
        NEED_WIN: MY_SCISSOR
    } , 
    OPONENT_SCISSOR : {
        NEED_LOSE: MY_PAPER, 
        NEED_DRAW: MY_SCISSOR, 
        NEED_WIN: MY_ROCK
    } 
}

with open('input.txt') as f:
    total_score_part1 = 0
    total_score_part2 = 0
    for line in f:
        (his, col2) = line.strip().split(" ")
        score = outcome[his][col2] + granted_per_shape[col2]
        total_score_part1 += score

        choice_required = cheating_code[his][col2]
        score_cheated = outcome[his][choice_required] + granted_per_shape[choice_required]
        total_score_part2 += score_cheated
        #print('Line: {} score cheated: {}'.format(line.strip(), score_cheated))
    
    print('Final score part1: {}'.format(total_score_part1))
    print('Final score part2: {}'.format(total_score_part2))
