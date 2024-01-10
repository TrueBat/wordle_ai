<h1>TURTUR</h1>
Turtur is the name i chose for my wordle algorithm. Each character value is a scale from 0 to 100 on how close is this character to being in exactly 50% of the remaining possible words, characters that appear in non of the words or appear in all of the word both have value of 0.The value of a word is the sum of all the values of its character excluding duplicate letters, a word has extra 1 value if its from the possible words.

the algorithm gets a 100% win rate with an average of 3.5 words. But the algorithm is not perfect.
<h3>Possible improvements:</h3>

1. Less value for letters that always appear together. For example consider 2 letters always appearing in the same half of the remaining words; the value of each will be 100 therefore together it'll be 200 although adding the second letter doesnt add any additional information.

2. Removing words that already appeared in wordle. Wordle doesn't use the same word twice, so if we remove the words that already appeared we can reduce the possible words making the algorithm faster.
