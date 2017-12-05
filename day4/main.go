package main

import (
	"bufio"
	"log"
	"os"
	"fmt"
	"strings"
)

func main()  {
	passphrases := readInputAsArray()
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

func readInputAsArray() []string {
	file, err := os.Open("day4.in")
	if err != nil {
		log.Fatal("Error opening input file")
	}
	scanner := bufio.NewScanner(file)
	var passphrases []string

	for scanner.Scan() {
		passphrases = append(passphrases, scanner.Text())
	}
	return passphrases
}