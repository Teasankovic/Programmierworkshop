// ===== Grundsetup =====
const board = document.getElementById("board");
const boardWidth = 600;
const boardHeight = 600;
const context = board.getContext("2d");

const hitButton = document.getElementById("hitButton");
const stayButton = document.getElementById("stayButton");
const restartButton = document.getElementById("restartButton");

// ===== Konfiguration =====
let startPlayerMoney = 1000;
const currency = "$";

// ===== Karten =====
const cardWidth = 110;
const cardHeight = 154;

class Card {
    constructor(value, type) {
        this.value = value;
        this.type = type;
    }

    toString() {
        return this.value + "-" + this.type;
    }

    getValue() {
        if ("AJQK".includes(this.value)) {
            if (this.value.toUpperCase() === "A") {
                return 11;
            }
            return 10;
        }
        return parseInt(this.value);
    }

    isAce() {
        return this.value.toUpperCase() === "A";
    }

    getImagePath() {
        return "./Cards/" + this.value + "-" + this.type + ".png";
    }
}

// ===== Deck & Hände =====
let deck = [];
let hiddenCard;
let dealerHand = [];
let dealerSum = 0;
let dealerAceCount = 0;

let playerHand = [];
let playerSum = 0;
let playerAceCount = 0;

// ===== Bild-Cache =====
const imageCache = {};

function getImage(path) {
    if (!imageCache[path]) {
        const img = new Image();
        img.src = path;
        img.onload = () => draw();
        imageCache[path] = img;
    }
    return imageCache[path];
}

// ===== Deck bauen & mischen =====
function buildDeck() {
    deck = [];
    const values = ["A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"];
    const types = ["C", "D", "H", "S"];

    for (let i = 0; i < types.length; i++) {
        for (let j = 0; j < values.length; j++) {
            deck.push(new Card(values[j], types[i]));
        }
    }
}

function shuffleDeck() {
    for (let i = 0; i < deck.length; i++) {
        const j = Math.floor(Math.random() * deck.length);
        const currCard = deck[i];
        deck[i] = deck[j];
        deck[j] = currCard;
    }
}

// ===== Ass-Reduzierung =====
function reducePlayerAce() {
    while (playerSum > 21 && playerAceCount > 0) {
        playerSum -= 10;
        playerAceCount -= 1;
    }
    return playerSum;
}

function reduceDealerAce() {
    while (dealerSum > 21 && dealerAceCount > 0) {
        dealerSum -= 10;
        dealerAceCount -= 1;
    }
    return dealerSum;
}

// ===== Spiel starten =====
function startGame() {
    buildDeck();
    shuffleDeck();

    // Dealer
    dealerHand = [];
    dealerSum = 0;
    dealerAceCount = 0;

    hiddenCard = deck.pop();
    dealerSum += hiddenCard.getValue();
    dealerAceCount += hiddenCard.isAce() ? 1 : 0;

    let card = deck.pop();
    dealerSum += card.getValue();
    dealerAceCount += card.isAce() ? 1 : 0;
    dealerHand.push(card);

    // Spieler
    playerHand = [];
    playerSum = 0;
    playerAceCount = 0;

    for (let i = 0; i < 2; i++) {
        card = deck.pop();
        playerSum += card.getValue();
        playerAceCount += card.isAce() ? 1 : 0;
        playerHand.push(card);
    }
}

// ===== Zeichnen =====
function draw() {
    context.clearRect(0, 0, boardWidth, boardHeight);

    // Versteckte Karte vom Dealer
    let hiddenCardImg = getImage("./Cards/BACK.png");
    if (stayButton.disabled) {
        hiddenCardImg = getImage(hiddenCard.getImagePath());
    }
    context.drawImage(hiddenCardImg, 20, 20, cardWidth, cardHeight);

    // Dealer-Hand zeichnen
    for (let i = 0; i < dealerHand.length; i++) {
        const card = dealerHand[i];
        const cardImg = getImage(card.getImagePath());
        context.drawImage(cardImg, cardWidth + 30 + (cardWidth + 5) * i, 20, cardWidth, cardHeight);
    }

    // Spieler-Hand zeichnen
    for (let i = 0; i < playerHand.length; i++) {
        const card = playerHand[i];
        const cardImg = getImage(card.getImagePath());
        context.drawImage(cardImg, 20 + (cardWidth + 5) * i, 320, cardWidth, cardHeight);
    }

    // Geld anzeigen
    context.font = "bold 14px Arial";
    context.fillStyle = "white";
    context.fillText("Player Money total: " + currency + startPlayerMoney, 200, 501);

    // Spieler-Kartensumme anzeigen
    context.font = "bold 15px Arial";
    context.fillStyle = "white";
    context.fillText("Player Card total: " + reducePlayerAce(), 400, 500);

    // Auswertung wenn Runde beendet
    if (stayButton.disabled) {
        dealerSum = reduceDealerAce();
        playerSum = reducePlayerAce();

        let message = "";
        if (playerSum > 21) {
            message = "You Lose!";
            startPlayerMoney -= 320;
        } else if (dealerSum > 21) {
            message = "You Win!";
            startPlayerMoney += 300;
        } else if (playerSum === dealerSum) {
            message = "Tie!";
            startPlayerMoney += 150;
        } else if (playerSum > dealerSum) {
            message = "You Win!";
            startPlayerMoney += 300;
        } else if (playerSum < dealerSum) {
            message = "You Lose!";
            startPlayerMoney -= 320;
        }

        context.font = "bold 30px Arial";
        context.fillStyle = "white";
        context.fillText(message, 200, 250);

        const playerLose = playerSum < dealerSum || playerSum > 21;
        const playerWin = playerSum > dealerSum || dealerSum > 21;

        context.font = "bold 14px Arial";
        context.fillStyle = "white";

        if (startPlayerMoney < -200 && playerLose) {
            context.fillText("You're getting yourself into serious debt my friend!", 100, 280);
        } else if (startPlayerMoney <= 50) {
            context.fillText("You're getting close to ground ZERO!", 100, 280);
        } else if (startPlayerMoney <= 320) {
            context.fillText("You don't have enough Money to Lose anymore!", 90, 280);
            context.fillText("Play right, your Money depends on it", 150, 300);
        }

        if (startPlayerMoney >= 3000 && playerWin) {
            context.fillText("You are whatever you want to be King", 150, 280);
        } else if (startPlayerMoney >= 2200 && playerWin) {
            context.fillText("Don't think that you're a pro...", 150, 280);
        } else if (startPlayerMoney >= 1600 && playerWin) {
            context.fillText("On a winning streak huh?", 170, 280);
        }
    }
}

// ===== Button-Logik: Hit =====
hitButton.addEventListener("click", () => {
    const card = deck.pop();
    playerSum += card.getValue();
    playerAceCount += card.isAce() ? 1 : 0;
    playerHand.push(card);

    if (reducePlayerAce() >= 21) {
        hitButton.disabled = true;
        stayButton.disabled = true;
    }

    draw();
});

// ===== Button-Logik: Stay =====
stayButton.addEventListener("click", () => {
    hitButton.disabled = true;
    stayButton.disabled = true;

    while (dealerSum < 17) {
        const card = deck.pop();
        dealerSum += card.getValue();
        dealerAceCount += card.isAce() ? 1 : 0;
        dealerHand.push(card);
    }

    draw();
});

// ===== Button-Logik: Neues Spiel =====
restartButton.addEventListener("click", () => {
    startGame();
    hitButton.disabled = false;
    stayButton.disabled = false;
    draw();
});

// ===== Spiel initial starten =====
startGame();
draw();
