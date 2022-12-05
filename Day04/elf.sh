#!/bin/bash

FILE=input.txt

num_of_fully_overlaps=0
num_of_partial_overlaps=0

is_fully_overlaped () {
    if ([ $1 -le $3 ] && [ $2 -ge $4 ] ) || ( [ $3 -le $1 ] && [ $4 -ge $2 ] )
    then
        return 0
    else
        return 1
    fi
}

is_partially_overlaped () {
    #echo "Is partially overlaps? $1 $2  compared to $3 $4"
    if ([ $3 -ge $1 ] && [ $3 -le $2 ]) || ( [ $4 -ge $1 ] && [ $4 -le $2 ])
    then
        return 0
    elif ([ $1 -ge $3 ] && [ $1 -le $4 ]) || ([ $2 -ge $3 ] && [ $2 -le $4 ])
    then    
        return 0
    else
        return 1
    fi
}

for line in `cat $FILE | sed 's/,/-/g'`; do
    arrSECTIONS=(${line//-/ })
    is_fully_overlaped ${arrSECTIONS[0]} ${arrSECTIONS[1]} ${arrSECTIONS[2]} ${arrSECTIONS[3]}
    is_full=$?
    is_partially_overlaped ${arrSECTIONS[0]} ${arrSECTIONS[1]} ${arrSECTIONS[2]} ${arrSECTIONS[3]}
    is_partially=$?
    if [  $is_full -eq 0 ] 
    then
        num_of_fully_overlaps=$((num_of_fully_overlaps+1))
        num_of_partial_overlaps=$((num_of_partial_overlaps+1))
    elif [  $is_partially  -eq 0 ]
    then
        num_of_partial_overlaps=$((num_of_partial_overlaps+1))
    fi
done

echo "Full overlaps: $num_of_fully_overlaps"
echo "Partial overlaps: $num_of_partial_overlaps"