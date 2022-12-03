#!/usr/bin/env perl

use strict;
use warnings;

my %letter_values;

#these are the ASCII intervals for a-z A-Z
for(((65..90),(97..122))){
    my $ascii_val = $_;
    my $char = chr($ascii_val);
    my $priority;
    if ($char =~ /[A-Z]/) {
        $priority  = $ascii_val - 64 + 26;
    }
    else{
        $priority  = $ascii_val - 70 - 26;
    }
    $letter_values{$char} = $priority;
    #print("ASCII $ascii_val is char: $char with elf priority $priority\n");
}

my $filename = 'sample.txt';

open(FH, '<', $filename) or die $!;

my $sum_of_priorities = 0;

while(<FH>){
    my $line = $_;
    chomp $line;
    my $line_size = length $line;

    my $first_part = substr $line, 0, ($line_size/2);
    my $second_part = substr $line, ($line_size/2), $line_size;

    my @first_part_letters = split //, $first_part;
    my @second_part_letters = split //, $second_part;

    my %found;
    for(@first_part_letters){
        my $letter = $_;
        if ( !$found{$letter} && grep( /^$letter$/, @second_part_letters ) ) {
            my $p = $letter_values{$letter};
            $sum_of_priorities += $p;
            $found{$letter} = 1;
            #print "found $letter with value $p on ";
        }
    }

    #print("line $line size of $line_size - parts: $first_part and $second_part \n");

}
close(FH);

print "First part $sum_of_priorities\n";


