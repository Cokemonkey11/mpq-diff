package de.inwc.mpqdiff

import java.io._

import java.security.MessageDigest
import java.nio.file.{Files, Paths}

import systems.crigges.jmpq3.JMpqEditor

object Generator {
  implicit class Helper(val sc: StringContext) extends AnyVal {
    def md5():    String = generate("MD5",     sc.parts(0))
    def sha():    String = generate("SHA",     sc.parts(0))
    def sha256(): String = generate("SHA-256", sc.parts(0))
  }
  // t is the type of checksum, i.e. MD5, or SHA-512 or whatever
  // path is the path to the file you want to get the hash of
  def generate(t: String, path: String): String = {
    val arr = Files readAllBytes (Paths get path)
    val checksum = MessageDigest.getInstance(t) digest arr
    checksum.map("%02X" format _).mkString
  }
}

object MpqDiff extends App {
  println(s"\n  Begin, with arguments [%s].".format(args.mkString(", ")))

  if (args.length != 2) {
    println("\n  Invalid arguments. See README.md for usage.")
  } else {
    val editors = args.map { arg: String =>
      println(s"\n  Opening editor of $arg.")
      new JMpqEditor(new File(arg))
    }

    val temp_listings = editors.zipWithIndex.map { case (editor: JMpqEditor, index: Int) =>
      println(s"\n  Extracting files from editor $index.")

      val f = new File("temp-" + index)
      if (f.isDirectory) {
        f.listFiles.foreach { _.delete }
        f.delete
      }

      f.mkdir
      new File("temp-" + index + File.separator + "(attributes)").createNewFile
      editor.extractAllFiles(f)

      f.listFiles.filter { _.isFile }
    }

    val comp_files:  Array[Array[File]]   = temp_listings.transpose
    val comp_hashes: Array[Array[String]] = comp_files.map { file_pair =>
      file_pair.map { file => Generator.generate("md5", file.getPath) }
    }

    comp_files.zip(comp_hashes).filter { case (files: Array[File], hashes: Array[String]) =>
      (files.length < 2 || hashes.length < 2 || files(0) != files(1)) || (hashes(0) != hashes(1))
    }.foreach { case (files, hashes) =>
      val fnames = files.map(_.getName)
      println(s"  Files differ: %s,\t%s\t\t(%s - %s).".format(fnames(0), fnames(1), hashes(0), hashes(1)))
    }
  }
}
