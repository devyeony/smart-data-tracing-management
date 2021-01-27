<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <script type="text/javascript">
      // Depending on the URL argument, test the compressed or uncompressed version.
      var compressed = (document.location.search == '?compressed');
      if (compressed) {
        document.write('<TITLE>Test harness for diff_match_patch.js</TITLE>');
        document.write('<scr'+'ipt type="text/javascript" src="../diff_match_patch.js"></scr'+'ipt>');
      } else {
        document.write('<TITLE>Test harness for diff_match_patch_uncompressed.js</TITLE>');
        document.write('<scr'+'ipt type="text/javascript" src="../diff_match_patch_uncompressed.js"></scr'+'ipt>');
      }
    </script>

    <script type="text/javascript" src="diff_match_patch_test.js"></script>

    <script type="text/javascript">
      // Counters for unit test results.
      var test_good = 0;
      var test_bad = 0;

      // If expected and actual are the identical, print 'Ok', otherwise 'Fail!'
      function assertEquals(msg, expected, actual) {
        if (typeof actual == 'undefined') {
          // msg is optional.
          actual = expected;
          expected = msg;
          msg = 'Expected: \'' + expected + '\' Actual: \'' + actual + '\'';
        }
        if (expected === actual) {
          document.write('<FONT COLOR="#009900">Ok</FONT><BR>');
          test_good++;
          return true;
        } else {
          document.write('<FONT COLOR="#990000"><BIG>Fail!</BIG></FONT><BR>');
          msg = msg.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
          document.write('<code>' + msg + '</code><BR>');
          test_bad++;
          return false;
        }
      }

      function assertTrue(msg, actual) {
        if (typeof actual == 'undefined') {
          // msg is optional.
          actual = msg;
          return assertEquals(true, actual);
        } else {
          return assertEquals(msg, true, actual);
        }
      }

      function assertFalse(msg, actual) {
        if (typeof actual == 'undefined') {
          // msg is optional.
          actual = msg;
          return assertEquals(false, actual);
        } else {
          return assertEquals(msg, false, actual);
        }
      }

      function runTests() {
        for (var x = 0; x < tests.length; x++) {
          document.write('<H3>' + tests[x] + ':</H3>');
          eval(tests[x] + '()');
        }
      }

      var tests = [
          'testDiffCommonPrefix',
          'testDiffCommonSuffix',
          'testDiffCommonOverlap',
          'testDiffHalfMatch',
          'testDiffLinesToChars',
          'testDiffCharsToLines',
          'testDiffCleanupMerge',
          'testDiffCleanupSemanticLossless',
          'testDiffCleanupSemantic',
          'testDiffCleanupEfficiency',
          'testDiffPrettyHtml',
          'testDiffText',
          'testDiffDelta',
          'testDiffXIndex',
          'testDiffLevenshtein',
          'testDiffBisect',
          'testDiffMain',

          'testMatchAlphabet',
          'testMatchBitap',
          'testMatchMain',

          'testPatchObj',
          'testPatchFromText',
          'testPatchToText',
          'testPatchAddContext',
          'testPatchMake',
          'testPatchSplitMax',
          'testPatchAddPadding',
          'testPatchApply'];

    </script>
  </head>
  <body>

    <script type="text/javascript">
      if (compressed) {
        document.write('<H1>Test harness for diff_match_patch.js</H1>');
        document.write('[ Switch to <A HREF="?uncompressed">Uncompressed</A>. ]');
      } else {
        document.write('<H1>Test harness for diff_match_patch_uncompressed.js</H1>');
        document.write('[ Switch to <A HREF="?compressed">Compressed</A>. ]');
      }
    </script>

    <P>If debugging errors, start with the first reported error,
    subsequent tests often rely on earlier ones.</P>

    <script type="text/javascript">
      var startTime = (new Date()).getTime();
      runTests();
      var endTime = (new Date()).getTime();
      document.write('<H3>Done.</H3>');
      document.write('<P>Tests passed: ' + test_good + '<BR>Tests failed: ' + test_bad + '</P>');
      document.write('<P>Total time: ' + (endTime - startTime) + ' ms</P>');
    </script>
  </body>
</html>