package example

import java.io._

import systems.crigges.jmpq3.JMpqEditor

object MpqDiff extends App {
  val editors = args.map { arg: String =>
    println(arg);
    new JMpqEditor(new File(arg))
  }

  val temp_listings = editors.zipWithIndex.map { case (editor: JMpqEditor, index: Int) =>
    println(editor);
    println(index);

    val f = new File("temp-" + index);
    f.mkdir;
    editor.extractAllFiles(f);

    f.listFiles
  }

  println(temp_listings.transpose.head.head.getName)
}
