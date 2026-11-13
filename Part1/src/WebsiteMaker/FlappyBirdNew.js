// ===== Grundsetup =====
const board = document.getElementById("board");
const boardWidth = 360;
const boardHeight = 640;
const context = board.getContext("2d");

// ===== Bilder laden =====
const backgroundImg = new Image();
backgroundImg.src = "flappybirdbg.png";

const birdImg = new Image();
birdImg.src = "flappybird.png";

const topPipeImg = new Image();
topPipeImg.src = "toppipe.png";

const bottomPipeImg = new Image();
bottomPipeImg.src = "bottompipe.png";

// ===== Vogel =====
const birdWidth = 34;
const birdHeight = 24;
const birdStartX = boardWidth / 8;
const birdStartY = boardHeight / 2;

let bird = {
    x: birdStartX,
    y: birdStartY,
    width: birdWidth,
    height: birdHeight,
    img: birdImg
};

// ===== Pipes =====
const pipeWidth = 64;
const pipeHeight = 512;
const pipeStartX = boardWidth;
const pipeStartY = 0;
let pipes = [];

// ===== Spiellogik =====
let velocityX = -4;
let velocityY = 0;
const gravity = 1;
let gameOver = false;
let score = 0;
let highscore = parseInt(localStorage.getItem("highscore")) || 0;

// ===== Timer-Referenzen =====
let gameLoopId;
let placePipesIntervalId;

// ===== Pipe erzeugen =====
function placePipes() {
    const randomPipeY = pipeStartY - pipeHeight / 4 - Math.random() * (pipeHeight / 2);
    const openingSpace = boardHeight / 4;

    const topPipe = {
        x: pipeStartX,
        y: randomPipeY,
        width: pipeWidth,
        height: pipeHeight,
        img: topPipeImg,
        passed: false
    };
    pipes.push(topPipe);

    const bottomPipe = {
        x: pipeStartX,
        y: randomPipeY + pipeHeight + openingSpace,
        width: pipeWidth,
        height: pipeHeight,
        img: bottomPipeImg,
        passed: false
    };
    pipes.push(bottomPipe);
}

// ===== Kollisionserkennung =====
function collision(a, b) {
    return (
        a.x < b.x + b.width &&
        a.x + a.width > b.x &&
        a.y < b.y + b.height &&
        a.y + a.height > b.y
    );
}

// ===== Highscore aktualisieren =====
function updateHighscore() {
    const currentScore = Math.floor(score);
    if (currentScore > highscore) {
        highscore = currentScore;
        localStorage.setItem("highscore", highscore);
    }
}

// ===== Bewegung & Logik =====
function move() {
    velocityY += gravity;
    bird.y += velocityY;
    bird.y = Math.max(bird.y, 0);

    for (let i = 0; i < pipes.length; i++) {
        const pipe = pipes[i];
        pipe.x += velocityX;

        if (!pipe.passed && bird.x > pipe.x + pipe.width) {
            pipe.passed = true;
            score += 0.5;
            updateHighscore();
        }

        if (collision(bird, pipe)) {
            gameOver = true;
        }
    }

    pipes = pipes.filter(pipe => pipe.x + pipe.width > 0);

    if (bird.y > boardHeight) {
        gameOver = true;
    }
}

// ===== Text mit Outline zeichnen =====
function drawOutlinedString(text, x, y) {
    context.fillStyle = "black";
    context.fillText(text, x - 2, y);
    context.fillText(text, x + 2, y);
    context.fillText(text, x, y - 2);
    context.fillText(text, x, y + 2);
    context.fillText(text, x - 2, y - 2);
    context.fillText(text, x + 2, y - 2);
    context.fillText(text, x - 2, y + 2);
    context.fillText(text, x + 2, y + 2);

    context.fillStyle = "white";
    context.fillText(text, x, y);
}

// ===== Zeichnen =====
function draw() {
    context.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight);
    context.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height);

    for (let i = 0; i < pipes.length; i++) {
        const pipe = pipes[i];
        context.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height);
    }

    context.font = "32px 'FlappyBirdFont', Arial";
    context.textBaseline = "alphabetic";

    if (gameOver) {
        drawOutlinedString("Game Over", 75, 250);
        drawOutlinedString("Score: " + Math.floor(score), 85, 300);
        drawOutlinedString("Highscore: " + highscore, 65, 350);
        context.font = "18px 'FlappyBirdFont', Arial";
        drawOutlinedString("Drücke SPACE zum Neustarten", 55, 400);
    } else {
        drawOutlinedString("Score: " + Math.floor(score), 10, 35);
        drawOutlinedString("Highscore: " + highscore, 10, 70);
    }
}

// ===== Game Loop =====
function update() {
    if (!gameOver) {
        move();
        draw();
        gameLoopId = requestAnimationFrame(update);
    } else {
        draw();
        clearInterval(placePipesIntervalId);
    }
}

// ===== Spiel starten =====
function startGame() {
    placePipesIntervalId = setInterval(placePipes, 1500);
    gameLoopId = requestAnimationFrame(update);
}

// ===== Spiel neustarten =====
function restartGame() {
    bird.y = birdStartY;
    velocityY = 0;
    pipes = [];
    score = 0;
    gameOver = false;
    startGame();
}

// ===== Tastatursteuerung =====
document.addEventListener("keydown", (e) => {
    if (e.code === "Space") {
        e.preventDefault();
        if (gameOver) {
            restartGame();
        } else {
            velocityY = -9;
        }
    }
});

// ===== Spiel initial starten =====
startGame();
