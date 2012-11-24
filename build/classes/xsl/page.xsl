<!-- code adapted from thread found here: http://www.sitepoint.com/forums/showthread.php?534249-Pagination-with-XSL -->
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
       
    <!-- these two parameters would be changed in server side code to go from page to page -->
    <xsl:param name="Page" select="0" />
    <xsl:param name="PageSize" select="20" />
       
    <xsl:template match="/" >     
        <xsl:variable name="mycount" select="count(//var)"/>
        <xsl:variable name="selectedRowCount" select="round($mycount div $PageSize)"/>     
        <xsl:element name="xml">              
	        <xsl:for-each select="//var" >            
	            <xsl:if test="position() &gt;= ($Page * $PageSize) + 1">
	                <xsl:if test="position() &lt;= $PageSize + ($PageSize * $Page)">  
	                    <xsl:element name="var" >
	                       <xsl:attribute name="name">
	                           <xsl:value-of select="@name"></xsl:value-of>
	                       </xsl:attribute>
	                       <xsl:attribute name="codeBook">
	                           <xsl:value-of select="../../docDscr/citation/titlStmt/titl"></xsl:value-of>
	                       </xsl:attribute>
	                       <xsl:copy-of select="./labl"/>
	                    </xsl:element>                     
	                </xsl:if>
	            </xsl:if>               
	        </xsl:for-each>
        </xsl:element>   
    </xsl:template>
</xsl:stylesheet>