package main

import (
	"fmt"
	"strings"
	"../lib"
)

func main() {
	passphrases := lib.ReadInputAsArray()
	validPassphraseCount := 0

	for _, passphrase := range passphrases {
		if isValidPassphrase(passphrase) {
			validPassphraseCount++
		}
	}
	fmt.Println(validPassphraseCount)
}

func isValidPassphrase(passphrase string) bool {
	words := strings.Fields(passphrase)
	encounteredWords := make(map[string]int)
	for _, word := range words {
		if _, ok := encounteredWords[word]; ok {
			return false
		}
		encounteredWords[word] = 1
	}
	return true
}
