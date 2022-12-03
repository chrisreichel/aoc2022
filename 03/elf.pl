#!/usr/bin/env perl

use strict;
use warnings;

my %letter_values;

#my @ascii_letterss = ((65..90),(97..122));
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

while ( my ($k,$v) = each %letter_values ) {
    print "$k => $v\n";
}

#for(@ascii_num_list_uc){
#    my $char_num = $_;
#    my $char = chr($char_num);
##    my $num  = $char_num - 64;
#    print("AsciimUC: NUM: $char_num and char: $char - $num \n");
#}

#my @ascii_num_list_lc = (97..122);
#for(@ascii_num_list_lc){
#    my $char_num = $_;
#    my $char = chr($char_num);
#    my $num  = $char_num - 70;
#    print("Ascii LC: NUM: $char_num and char: $char - $num\n");
#}


my $filename = 'sample.txt';

open(FH, '<', $filename) or die $!;

while(<FH>){
    my $line = $_;
    chomp $line ;
    my $line_size = length $line;

    my $first_part = substr $line, 0, ($line_size/2);
    my $second_part = substr $line, ($line_size/2), $line_size;

    my @first_part_letters = split //, $first_part;
    my @second_part_letters = split //, $second_part;
    for(@first_part_letters){
        my $letter = $_;
        if ( grep( /^$letter$/, @second_part_letters ) ) {
            print "found $letter on ";
        }
    }

    print("Line $line size of $line_size - parts: $first_part and $second_part \n");

}

close(FH);