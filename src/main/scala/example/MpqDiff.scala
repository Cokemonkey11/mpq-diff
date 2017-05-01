package de.inwc.mpqdiff

import java.io._

import systems.crigges.jmpq3.JMpqEditor

object MpqDiff extends App {
  println(s"\n  Begin, with arguments [%s].".format(args.mkString(", ")))

  if (args.length != 2) {
    println("  Invalid arguments. See README.md for usage.")
  } else {
    val editors = args.map { arg: String =>
      println(s"  $arg");
      new JMpqEditor(new File(arg))
    }

    val temp_listings = editors.zipWithIndex.map { case (editor: JMpqEditor, index: Int) =>
      println(s"  $editor");
      println(s"  $index");

      val f = new File("temp-" + index);
      f.mkdir;
      editor.extractAllFiles(f);

      f.listFiles
    }

    println(s"  %s".format(temp_listings.transpose.head.head.getName))
  }
}
