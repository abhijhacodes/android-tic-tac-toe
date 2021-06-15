package com.example.tictactoe

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var buttons: Array<Array<ImageButton>>
    lateinit var player1textview: TextView
    lateinit var player2textview: TextView

    private var playerturn: Boolean = true
    private var roundcount: Int = 0
    private var player1points: Int = 0
    private var player2points: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        player1textview = player1Text
        player2textview = player2Text

        buttons = Array(3){r->
            Array(3){c->
                initButtons(r,c)
            }
        }

        resetgame.setOnClickListener{
            player1points = 0
            player2points = 0
            updateScore()
            clearBoard()
        }
    }

    private fun initButtons(r: Int, c:Int):ImageButton {
        val btn: ImageButton = findViewById(resources.getIdentifier("btn$r$c", "id", packageName))
        btn.setOnClickListener{
            onBtnclick(btn)
        }
        return btn
    }

    private fun onBtnclick(btn: ImageButton) {
        if(btn.drawable != null) return
        if(playerturn){
            btn.setImageResource(R.drawable.x)
        }
        else{
            btn.setImageResource(R.drawable.o)
        }
        roundcount++

        if(checkwin()){
            if(playerturn) win(1) else win(2)
        }
        else if(roundcount==9){
            draw()
        }
        else {
            playerturn = !playerturn
        }
    }

    private fun win(player: Int) {
        if(player==1) player1points++ else player2points++
        Toast.makeText(applicationContext, "Player $player won!", Toast.LENGTH_LONG).show()
        updateScore()
        clearBoard()
    }

    private fun updateScore() {
        player1textview.text = "Player 1: $player1points"
        player2textview.text = "Player 2: $player2points"
    }

    private fun clearBoard() {
        for (i in 0..2){
            for (j in 0..2){
                buttons[i][j].setImageResource(0)
            }
        }
        roundcount++
        playerturn = true
    }

    private fun draw() {
        Toast.makeText(applicationContext, "Match drawn!", Toast.LENGTH_LONG).show()
        clearBoard()
    }

    private fun checkwin(): Boolean {
        val fields = Array(3){r->
            Array(3){c->
                getField(buttons[r][c])
            }
        }

        for(i in 0..2){
            if((fields[i][0] == fields[i][1]) &&
                (fields[i][0] == fields[i][2]) &&
                (fields[i][0] != null)
            ) return true
        }

        for(i in 0..2){
            if((fields[0][i] == fields[1][i]) &&
                (fields[0][i] == fields[2][i]) &&
                (fields[0][i] != null)
            ) return true
        }

        if((fields[0][0] == fields[1][1]) &&
            (fields[0][0] == fields[2][2]) &&
            (fields[0][0] != null)
        ) return true

        if((fields[0][2] == fields[1][1]) &&
            (fields[0][2] == fields[2][0]) &&
            (fields[0][2] != null)
        ) return true

        return false
    }

    private fun getField(btn: ImageButton):Char? {
        val drw: Drawable? = btn.drawable
        val drawx: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.x, null)
        val drawo: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.o, null)

        return when(drw?.constantState){
            drawx?.constantState -> 'X'
            drawo?.constantState -> 'O'
            else -> null
        }
    }
}