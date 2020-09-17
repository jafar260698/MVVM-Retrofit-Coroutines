package com.example.jobvacant.ui.fragment

import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.jobvacant.R
import com.example.jobvacant.model.Question
import com.example.jobvacant.util.Constants
import kotlinx.android.synthetic.main.fragment_quiz.*

class QuizFragment : Fragment(),View.OnClickListener {
    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mCorrectAnswers: Int = 0
    private var mSelectedOptionPosition: Int = 0
    // TextView option
     var tv_option_one:TextView?=null
     var tv_option_two:TextView?=null
     var tv_option_three:TextView?=null
     var tv_option_four:TextView?=null
    var btn_submit:Button?=null
    var progressBar:ProgressBar?=null
    var tv_progress:TextView?=null
    var tv_question:TextView?=null
    var iv_image:ImageView?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater.inflate(R.layout.fragment_quiz, container, false)
        mQuestionsList = Constants.getQuestions()

        iv_image=view.findViewById(R.id.iv_image)
        tv_question=view.findViewById(R.id.tv_question)
        tv_progress=view.findViewById(R.id.tv_progress)
        progressBar=view.findViewById(R.id.progressBar)
        tv_option_one=view.findViewById(R.id.tv_option_one)
        tv_option_two=view.findViewById(R.id.tv_option_two)
        tv_option_three=view.findViewById(R.id.tv_option_three)
        tv_option_four=view.findViewById(R.id.tv_option_four)
        tv_option_one?.setOnClickListener(this)
        tv_option_two?.setOnClickListener(this)
        tv_option_three?.setOnClickListener(this)
        tv_option_four?.setOnClickListener(this)

        btn_submit =view.findViewById(R.id.btn_submit)
        btn_submit!!.setOnClickListener(this)
        setQuestion()
        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.tv_option_one -> {

                selectedOptionView(tv_option_one!!, 1)
            }

            R.id.tv_option_two -> {

                selectedOptionView(tv_option_two!!, 2)
            }

            R.id.tv_option_three -> {

                selectedOptionView(tv_option_three!!, 3)
            }

            R.id.tv_option_four -> {

                selectedOptionView(tv_option_four!!, 4)
            }

            // START
            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0) {

                    mCurrentPosition++
                    when {
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                           val action=QuizFragmentDirections.actionQuizFragmentToResultFragment(mCorrectAnswers,mQuestionsList!!.size)
                           this.findNavController().navigate(action)
                            Toast.makeText(requireActivity(),"Answers: $mCorrectAnswers + ${mQuestionsList!!.size}",Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition - 1)

                    // This is to check if the answer is wrong
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        mCorrectAnswers++
                    }

                    // This is for correct answer
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList!!.size) {
                        btn_submit?.text = "Yakunlash"
                    } else {
                        btn_submit?.text = "Keyingi savol"
                    }

                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun setQuestion() {
        val question = mQuestionsList!!.get(mCurrentPosition -1) // Getting the question from the list with the help of current position.
        defaultOptionsView()
        Log.e(TAG,question.toString())
        Log.e(TAG,"Current Position $mCurrentPosition")


        if (mCurrentPosition == mQuestionsList!!.size) {
            btn_submit?.text = "Tugatish"
        } else {
            btn_submit?.text = "OK"
        }
        // END

        progressBar?.progress = mCurrentPosition
        tv_progress?.text = "$mCurrentPosition" + "/" + progressBar?.getMax()

        tv_question?.text = question.question
        iv_image?.setImageResource(question.image)
        tv_option_one?.text = question.optionOne
        tv_option_two?.text = question.optionTwo
        tv_option_three?.text = question.optionThree
        tv_option_four?.text = question.optionFour

    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {

        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(
            Color.parseColor("#363A43")
        )
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            requireActivity(),
            R.drawable.selected_option_border_bg
        )
    }


    private fun defaultOptionsView() {
       /* val options = ArrayList<TextView>()
        options.add(0,tv_option_one!!)
        options.add(1, tv_option_two!!)
        options.add(2, tv_option_three!!)
        options.add(3, tv_option_four!!)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.default_option_border_bg
            )
        } */

        tv_option_one?.apply {
            setTextColor(Color.parseColor("#7A8089"))
            typeface = Typeface.DEFAULT
            background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.default_option_border_bg
            )
        }
        tv_option_two?.apply {
            setTextColor(Color.parseColor("#7A8089"))
            typeface = Typeface.DEFAULT
            background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.default_option_border_bg
            )
        }
        tv_option_three?.apply {
            setTextColor(Color.parseColor("#7A8089"))
            typeface = Typeface.DEFAULT
            background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.default_option_border_bg
            )
        }
        tv_option_four?.apply {
            setTextColor(Color.parseColor("#7A8089"))
            typeface = Typeface.DEFAULT
            background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.default_option_border_bg
            )
        }

    }


    private fun answerView(answer: Int, drawableView: Int) {

        when (answer) {

            1 -> {
                tv_option_one?.background = ContextCompat.getDrawable(
                    requireActivity(),
                    drawableView
                )
            }
            2 -> {
                tv_option_two?.background = ContextCompat.getDrawable(
                    requireActivity(),
                    drawableView
                )
            }
            3 -> {
                tv_option_three?.background = ContextCompat.getDrawable(
                    requireActivity(),
                    drawableView
                )
            }
            4 -> {
                tv_option_four?.background = ContextCompat.getDrawable(
                    requireActivity(),
                    drawableView
                )
            }
        }
    }
}